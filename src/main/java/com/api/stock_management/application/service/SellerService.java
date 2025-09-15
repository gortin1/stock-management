package com.api.stock_management.application.service;


import com.api.stock_management.domain.Sale;
import com.api.stock_management.domain.Seller;
import com.api.stock_management.domain.repository.ISeller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    @Autowired
    private ISeller iSeller;

//    @Autowired
//    private WhatsAppService whatsAppService;
//
//    @Autowired
//    private JwtUtil jwtUtil;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    public Seller createSeller(Seller seller){
        seller.setStatus(false);
        seller.setSenha(passwordEncoder.encode(seller.getSenha()));

        String codigo = whatsAppService.generateActivationCode();

        try {
            whatsAppService.sendActivationCode(seller.getCelular(),codigo);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar o codigo de ativação: ",e);
        }

        seller.setCodigoAtivacao();
        return iSeller.save(seller);
    }
    public Seller activateSeller(String celular, String codigo){
        Seller seller = iSeller.findByCelular(celular)
                .orElseThrow(()-> new RuntimeException("Seller não encontrado"));
        if (!codigo.equalsIgnoreCase(seller.getCodigoAtivacao())){
            throw new RuntimeException("Codigo inválido");
        }
        seller.setStatus(true);
        seller.setCodigoAtivacao(null);
        return iSeller.save(seller);
        
    }
    public String login(String email, String senha){
        Seller seller = iSeller.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Email não encontrado"));
        if (!passwordEncoder.matches(senha, seller.getSenha())){
            throw new RuntimeException("Senha incorreta");
        }
        if (!seller.isStatus()){
            throw new RuntimeException("Seller não ativo");
        }
        return jwtUtil.generateToken(email);
    }
}
