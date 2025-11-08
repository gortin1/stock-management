package com.api.stock_management.application.service;

import com.api.stock_management.application.dto.seller.SellerActivateRequestDTO;
import com.api.stock_management.application.dto.seller.SellerRequestDTO;
import com.api.stock_management.application.dto.seller.SellerResponseDTO;
import com.api.stock_management.domain.model.Seller;
import com.api.stock_management.domain.repository.SellerRepository;
import com.api.stock_management.infrastructure.messaging.TwilioService;
import jakarta.annotation.PostConstruct; // Importar
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException; // Importar
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // Importar
import org.springframework.security.core.context.SecurityContextHolder; // Importar
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile; // Importar
import org.springframework.web.servlet.support.ServletUriComponentsBuilder; // Importar

import java.nio.file.Path; // Importar
import java.nio.file.Paths; // Importar
import java.util.Random;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private TwilioService twilioService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private FileStorageService fileStorageService;

    //Não aguebtomais
    @Value("${file.upload-dir.sellers}")
    private String sellersUploadDir;

    private Path sellersPath;

    @PostConstruct
    public void init() {
        this.sellersPath = Paths.get(sellersUploadDir);
    }

    @Transactional
    public SellerResponseDTO createSeller(SellerRequestDTO sellerRequestDTO){
        if (sellerRepository.findByEmail(sellerRequestDTO.getEmail()).isPresent() || sellerRepository.findByCelular(sellerRequestDTO.getCelular()).isPresent()) {
            throw new EntityExistsException("Usuário já cadastrado.");
        }

        Seller newSeller = new Seller();
        newSeller.setNome(sellerRequestDTO.getNome());
        newSeller.setCnpj(sellerRequestDTO.getCnpj());
        newSeller.setEmail(sellerRequestDTO.getEmail());
        newSeller.setCelular(sellerRequestDTO.getCelular());
        newSeller.setSenha(passwordEncoder.encode(sellerRequestDTO.getSenha()));
        newSeller.setStatus(false);

        String codigo = twilioService.generateActivationCode();
        newSeller.setCodigoAtivacao(codigo);

        try {
            twilioService.sendActivationCode(newSeller.getCelular(), codigo);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar código de ativação: " + e);
        }

        Seller savedSeller = sellerRepository.save(newSeller);
        return new SellerResponseDTO(savedSeller);
    }

    @Transactional
    public SellerResponseDTO activateSeller(SellerActivateRequestDTO sellerActivateRequestDTO){
        Seller seller = sellerRepository.findByCelular(sellerActivateRequestDTO.getCelular())
                .orElseThrow(()-> new RuntimeException("Vendedor não encontrado."));

        if (seller.isStatus()) {
            throw new IllegalStateException("Esta conta já está ativa.");
        }

        if (!sellerActivateRequestDTO.getCodigoAtivacao().equals(seller.getCodigoAtivacao())) {
            throw new IllegalArgumentException("Codigo inválido");
        }

        seller.setStatus(true);
        seller.setCodigoAtivacao(null);

        Seller activatedSeller = sellerRepository.save(seller);
        return new SellerResponseDTO(activatedSeller);
    }

    // Upload de img
    @Transactional
    public SellerResponseDTO uploadProfileImage(MultipartFile file) {
        Seller authenticatedSeller = getAuthenticatedSeller();


        String fileName = fileStorageService.store(file, this.sellersPath);


        String urlImagem = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/sellers/")
                .path(fileName)
                .toUriString();


        authenticatedSeller.setImagem(urlImagem);
        sellerRepository.save(authenticatedSeller);

        return new SellerResponseDTO(authenticatedSeller);
    }

    private Seller getAuthenticatedSeller() {
        return (Seller) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}