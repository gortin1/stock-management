package com.api.stock_management.application.service;

import com.api.stock_management.application.dto.product.ProductRequestDTO;
import com.api.stock_management.application.dto.product.ProductResponseDTO;
import com.api.stock_management.domain.model.Product;
import com.api.stock_management.domain.model.Seller;
import com.api.stock_management.domain.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Seller authenticatedSeller = getAuthenticatedSeller();
        Product newProduct = new Product();
        newProduct.setNome(productRequestDTO.getNome());
        newProduct.setPreco(productRequestDTO.getPreco());
        newProduct.setQuantidade(productRequestDTO.getQuantidade());
        newProduct.setImagem(productRequestDTO.getImagem());
        newProduct.setSeller(authenticatedSeller);
        newProduct.setStatus(true);

        Product savedProduct = productRepository.save(newProduct);
        return new ProductResponseDTO(savedProduct);
    }

    @Transactional
    public List<ProductResponseDTO> getAllProductsBySeller() {
        Seller authenticatedSeller = getAuthenticatedSeller();
        List<Product> products = productRepository.findBySeller(authenticatedSeller);

        return products.stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponseDTO getProductById(Long id) {
        Product product = findProductByIdAndOwner(id, getAuthenticatedSeller());

        return new ProductResponseDTO(product);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product product = findProductByIdAndOwner(id, getAuthenticatedSeller());
        product.setNome(productRequestDTO.getNome());
        product.setPreco(productRequestDTO.getPreco());
        product.setQuantidade(productRequestDTO.getQuantidade());
        product.setImagem(productRequestDTO.getImagem());

        Product updateProduct = productRepository.save(product);
        return new ProductResponseDTO(updateProduct);
    }

    @Transactional
    public void inactiveProduct(Long id) {
        Product product = findProductByIdAndOwner(id, getAuthenticatedSeller());
        product.setStatus(false);

        productRepository.save(product);
    }

    private Seller getAuthenticatedSeller() {
        return (Seller) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Product findProductByIdAndOwner(Long productId, Seller owner) {
        return productRepository.findById(productId).filter(product -> product.getSeller().getId().equals(owner.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
    }
}
