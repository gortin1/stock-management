package com.api.stock_management.application.service;

import com.api.stock_management.application.dto.product.StatusProduct;
import com.api.stock_management.application.dto.sale.SaleRequestDTO;
import com.api.stock_management.application.dto.sale.SaleResponseDTO;
import com.api.stock_management.domain.model.Product;
import com.api.stock_management.domain.model.Sale;
import com.api.stock_management.domain.model.Seller;
import com.api.stock_management.domain.repository.ProductRepository;
import com.api.stock_management.domain.repository.SaleRepository;
import jakarta.persistence.EntityNotFoundException;
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

    @Transactional(readOnly = true)
    public List<SaleResponseDTO> getAllSalesBySeller(){
        Seller authenticatedSeller = getAuthenticatedSeller();
        List<Sale> sales = saleRepository.findBySeller(authenticatedSeller);

        return sales.stream()
                .map(SaleResponseDTO::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO) {
        Seller authenticatedSeller = (Seller) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Product product = productRepository.findById(saleRequestDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + saleRequestDTO.getProductId() + " não encontrado."));

        validateSale(saleRequestDTO, product, authenticatedSeller);

        int newQuantity = product.getQuantidade() - saleRequestDTO.getQuantidade();
        product.setQuantidade(newQuantity);
        productRepository.save(product);

        Sale newSale = new Sale();
        newSale.setProduct(product);
        newSale.setSeller(authenticatedSeller);
        newSale.setQuantidadeVendida(saleRequestDTO.getQuantidade());
        newSale.setPrecoNoMomentoDaVenda(product.getPreco());
        newSale.setDataDaVenda(LocalDateTime.now());

        Sale savedSale = saleRepository.save(newSale);
        return new SaleResponseDTO(savedSale);
    }

    private void validateSale(SaleRequestDTO saleRequestDTO, Product product, Seller seller) {
        if (!product.getSeller().getId().equals(seller.getId())) {
            throw new SecurityException("Produto não encontrado.");
        }
        if (product.getStatusProduto() == StatusProduct.INATIVO) {
            throw new IllegalStateException("Não é possível vender um produto inativo.");
        }

        if (product.getStatusProduto() == StatusProduct.EM_FALTA) {
            throw new IllegalStateException("Não é possível vender um produto que está em falta.");
        }
        if (product.getQuantidade() < saleRequestDTO.getQuantidade()) {
            throw new IllegalStateException("Estoque insuficiente. Quantidade disponível: " + product.getQuantidade());
        }
    }
    private Seller getAuthenticatedSeller() {
        return (Seller) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
