package com.api.stock_management.application.service;

import com.api.stock_management.application.dto.product.StatusProduct;
import com.api.stock_management.application.dto.sale.SaleItemRequestDTO;
import com.api.stock_management.application.dto.sale.SaleRequestDTO;
import com.api.stock_management.application.dto.sale.SaleResponseDTO;
import com.api.stock_management.domain.model.Pedido;
import com.api.stock_management.domain.model.Product;
import com.api.stock_management.domain.model.Sale;
import com.api.stock_management.domain.model.Seller;
import com.api.stock_management.domain.repository.PedidoRepository;
import com.api.stock_management.domain.repository.ProductRepository;
import com.api.stock_management.domain.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional(readOnly = true)
    public List<SaleResponseDTO> getAllSalesBySeller(){
        Seller authenticatedSeller = getAuthenticatedSeller();

        List<Sale> sales = saleRepository.findAllBySeller(authenticatedSeller);

        return sales.stream()
                .map(sale -> new SaleResponseDTO(sale, sale.getPedido()))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SaleResponseDTO> createSale(SaleRequestDTO saleRequestDTO) {
        Seller authenticatedSeller = getAuthenticatedSeller();

        Pedido newPedido = new Pedido();
        newPedido.setSeller(authenticatedSeller);
        newPedido.setDataPedido(LocalDateTime.now());
        Pedido savedPedido = pedidoRepository.save(newPedido);

        List<Sale> savedSalesItems = new ArrayList<>();

        for (SaleItemRequestDTO itemDTO : saleRequestDTO.getItems()) {

            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + itemDTO.getProductId() + " não encontrado."));

            validateSale(itemDTO, product, authenticatedSeller);

            int newQuantity = product.getQuantidade() - itemDTO.getQuantidade();
            product.setQuantidade(newQuantity);
            productRepository.save(product);

            Sale newSaleItem = new Sale();
            newSaleItem.setProduct(product);
            newSaleItem.setQuantidadeVendida(itemDTO.getQuantidade());
            newSaleItem.setPrecoNoMomentoDaVenda(product.getPreco());
            newSaleItem.setPedido(savedPedido);
            newSaleItem.setDataDaVenda(LocalDateTime.now());

            savedSalesItems.add(saleRepository.save(newSaleItem));
        }

        return savedSalesItems.stream()
                .map(sale -> new SaleResponseDTO(sale, sale.getPedido()))
                .collect(Collectors.toList());
    }

    private void validateSale(SaleItemRequestDTO saleItemDTO, Product product, Seller seller) {
        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new SecurityException("Produto não encontrado.");
        }

        if (product.getStatusProduto() == StatusProduct.INATIVO) {
            throw new IllegalStateException("Não é possível vender um produto inativo: " + product.getNome());
        }

        if (product.getStatusProduto() == StatusProduct.EM_FALTA) {
            throw new IllegalStateException("Não é possível vender um produto que está em falta: " + product.getNome());
        }
        if (product.getQuantidade() < saleItemDTO.getQuantidade()) {
            throw new IllegalStateException("Estoque insuficiente para " + product.getNome() + ". Quantidade disponível: " + product.getQuantidade());
        }
    }
    private Seller getAuthenticatedSeller() {
        return (Seller) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
