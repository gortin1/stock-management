package com.api.stock_management.application.service;

import com.api.stock_management.application.dto.product.ProductRequestDTO;
import com.api.stock_management.application.dto.product.ProductResponseDTO;
import com.api.stock_management.domain.model.Product;
import com.api.stock_management.domain.model.Seller;
import com.api.stock_management.domain.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Dependências de arquivo (MANTIDAS para o método antigo)
    @Autowired
    private StorageService fileStorageService;
    @Value("${file.upload-dir.produtos}")
    private String produtosUploadDir;
    private Path produtosPath;

    @PostConstruct
    public void init() {
        this.produtosPath = Paths.get(produtosUploadDir);
    }

    // --- Métodos Existentes ---
    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Seller authenticatedSeller = getAuthenticatedSeller();
        Product newProduct = new Product();
        newProduct.setNome(productRequestDTO.getNome());
        newProduct.setPreco(productRequestDTO.getPreco());
        newProduct.setQuantidade(productRequestDTO.getQuantidade());
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

        Product updateProduct = productRepository.save(product);
        return new ProductResponseDTO(updateProduct);
    }

    @Transactional
    public void inactiveProduct(Long id) {
        Product product = findProductByIdAndOwner(id, getAuthenticatedSeller());
        product.setStatus(false);

        productRepository.save(product);
    }

    // Método antigo (MultipartFile)
    @Transactional
    public ProductResponseDTO uploadImage(Long id, MultipartFile file) {
        Seller authenticatedSeller = getAuthenticatedSeller();
        Product product = findProductByIdAndOwner(id, authenticatedSeller);

        String fileName = fileStorageService.store(file, this.produtosPath);

        // url
        String urlImagem = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/produtos/")
                .path(fileName)
                .toUriString();

        // atualiza o banco com a url da imagem
        product.setImagem(urlImagem);
        productRepository.save(product);

        return new ProductResponseDTO(product);
    }

    // NOVO: Método para receber a URL e salvar no banco
    @Transactional
    public ProductResponseDTO setImageUrl(Long productId, String imageUrl) {
        Seller authenticatedSeller = getAuthenticatedSeller();

        // 1. Encontra o produto pelo ID e verifica se o Seller é o dono
        Product product = findProductByIdAndOwner(productId, authenticatedSeller);

        // 2. Define a URL da imagem no campo 'img' (no seu modelo é 'imagem')
        product.setImagem(imageUrl);

        // 3. Salva a alteração no banco de dados
        Product updatedProduct = productRepository.save(product);

        return new ProductResponseDTO(updatedProduct);
    }

    // --- Métodos Privados ---
    private Seller getAuthenticatedSeller() {
        return (Seller) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Product findProductByIdAndOwner(Long productId, Seller owner) {
        return productRepository.findById(productId).filter(product -> product.getSeller().getId().equals(owner.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
    }
}