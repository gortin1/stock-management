package com.api.stock_management.application.controller;

import java.util.List;
import com.api.stock_management.application.dto.product.ProductRequestDTO;
import com.api.stock_management.application.dto.product.ProductResponseDTO;
import com.api.stock_management.application.dto.product.ImageUrlRequestDTO; // NOVO
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.stock_management.application.service.ProductService;
import org.springframework.web.multipart.MultipartFile; // MANTIDO PARA OUTRAS POSSIBILIDADES, MAS NÃO USADO

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO productRequest) {
        ProductResponseDTO productResponse = productService.createProduct(productRequest);

        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> productsResponse = productService.getAllProductsBySeller();

        return ResponseEntity.ok(productsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO productResponse = productService.getProductById(id);

        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductRequestDTO productRequest) {
        ProductResponseDTO productResponse = productService.updateProduct(id, productRequest);

        return ResponseEntity.ok(productResponse);
    }

    @PatchMapping("/{id}/inactivate")
    public ResponseEntity<Void> inactiveProduct(@PathVariable Long id) {
        productService.inactiveProduct(id);

        return ResponseEntity.noContent().build();
    }

    // Antigo endpoint de upload de arquivo (MultipartFile)
    @PatchMapping("/{id}/image")
    public ResponseEntity<ProductResponseDTO> uploadImage(
            @PathVariable Long id,
            @RequestParam("imagem") MultipartFile imagem) {

        ProductResponseDTO productResponse = productService.uploadImage(id, imagem);
        return ResponseEntity.ok(productResponse);
    }


    // NOVO: Endpoint para adicionar URL de imagem via JSON
    @PatchMapping("/{id}/image-url")
    public ResponseEntity<ProductResponseDTO> setImageFromUrl(
            @PathVariable Long id,
            @RequestBody @Valid ImageUrlRequestDTO imageUrlRequest) {

        ProductResponseDTO productResponse = productService.setImageUrl(id, imageUrlRequest.getImageUrl());
        return ResponseEntity.ok(productResponse);
    }
}