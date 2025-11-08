package com.api.stock_management.application.service;

import com.api.stock_management.application.dto.product.ProductRequestDTO;
import com.api.stock_management.application.dto.product.ProductResponseDTO;
import com.api.stock_management.domain.model.Product;
import com.api.stock_management.domain.model.Seller;
import com.api.stock_management.domain.repository.ProductRepository;
import jakarta.annotation.PostConstruct; // Importar
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // Importar
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile; // Importar
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // Importar

import java.nio.file.Path; // Importar
import java.nio.file.Paths; // Importar
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // --- NOVA INJEÇÃO DO SERVIÇO DE ARQUIVOS ---
    @Autowired
    private FileStorageService fileStorageService;

    // --- NOVA INJEÇÃO DA CONFIGURAÇÃO DO application.properties ---
    @Value("${file.upload-dir.produtos}")
    private String produtosUploadDir;

    private Path produtosPath; // Variável para guardar o caminho completo

    // Este método roda assim que o serviço é criado, preparando o caminho
    @PostConstruct
    public void init() {
        this.produtosPath = Paths.get(produtosUploadDir);
    }

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Seller authenticatedSeller = getAuthenticatedSeller();
        Product newProduct = new Product();
        newProduct.setNome(productRequestDTO.getNome());
        newProduct.setPreco(productRequestDTO.getPreco());
        newProduct.setQuantidade(productRequestDTO.getQuantidade());
        // newProduct.setImagem(productRequestDTO.getImagem()); // LINHA REMOVIDA
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
        // product.setImagem(productRequestDTO.getImagem()); removi aqui

        Product updateProduct = productRepository.save(product);
        return new ProductResponseDTO(updateProduct);
    }

    @Transactional
    public void inactiveProduct(Long id) {
        Product product = findProductByIdAndOwner(id, getAuthenticatedSeller());
        product.setStatus(false);

        productRepository.save(product);
    }

    // Adcionadno upload de imaegm
    @Transactional
    public ProductResponseDTO uploadImage(Long id, MultipartFile file) {
        Seller authenticatedSeller = getAuthenticatedSeller();
        Product product = findProductByIdAndOwner(id, authenticatedSeller);


        String fileName = fileStorageService.store(file, this.produtosPath);

        //url
        String urlImagem = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/produtos/")
                .path(fileName)
                .toUriString();

        //atualiza o banco com a url da imagem
        product.setImagem(urlImagem);
        productRepository.save(product);

        return new ProductResponseDTO(product);
    }

    private Seller getAuthenticatedSeller() {
        return (Seller) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private Product findProductByIdAndOwner(Long productId, Seller owner) {
        return productRepository.findById(productId).filter(product -> product.getSeller().getId().equals(owner.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado."));
    }
}