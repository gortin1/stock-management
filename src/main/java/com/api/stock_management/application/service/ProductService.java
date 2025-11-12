package com.api.stock_management.application.service;

import com.api.stock_management.application.dto.product.ProductRequestDTO;
import com.api.stock_management.application.dto.product.ProductResponseDTO;
import com.api.stock_management.application.dto.product.StatusProduct; // 1. IMPORTAR O ENUM
import com.api.stock_management.domain.model.Product;
import com.api.stock_management.domain.model.Seller;
import com.api.stock_management.domain.repository.ProductRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) throws IOException { // Adicionado 'throws'
        Seller authenticatedSeller = getAuthenticatedSeller();
        Product newProduct = new Product();
        newProduct.setNome(productRequestDTO.getNome());
        newProduct.setPreco(productRequestDTO.getPreco());
        newProduct.setQuantidade(productRequestDTO.getQuantidade());
        newProduct.setSeller(authenticatedSeller);
        MultipartFile imagem = productRequestDTO.getImagem();
        if (imagem != null && !imagem.isEmpty()) {
            String imageUrl = uploadImage(imagem);
            newProduct.setImagem(imageUrl);
        }

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
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) throws IOException { // Adicionado 'throws'
        Product product = findProductByIdAndOwner(id, getAuthenticatedSeller());
        product.setNome(productRequestDTO.getNome());
        product.setPreco(productRequestDTO.getPreco());
        product.setQuantidade(productRequestDTO.getQuantidade());

        MultipartFile imagem = productRequestDTO.getImagem();
        if (imagem != null && !imagem.isEmpty()) {
            String imageUrl = uploadImage(imagem);
            product.setImagem(imageUrl);
        }

        Product updateProduct = productRepository.save(product);
        return new ProductResponseDTO(updateProduct);
    }

    @Transactional
    public void inactiveProduct(Long id) {
        Product product = findProductByIdAndOwner(id, getAuthenticatedSeller());

        product.setStatusProduto(StatusProduct.INATIVO); // Usa o Enum

        productRepository.save(product);
    }

    @Transactional
    public void activateProduct(Long id) {
        Product product = findProductByIdAndOwner(id, getAuthenticatedSeller());
        product.setStatusProduto(StatusProduct.ATIVO);

        productRepository.save(product);
    }

    private Seller getAuthenticatedSeller() {
        return (Seller) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Product findProductByIdAndOwner(Long productId, Seller owner) {
        return productRepository.findById(productId).filter(product -> product.getSeller().getId().equals(owner.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
    }

    private String uploadImage(MultipartFile file) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("secure_url");
    }
}