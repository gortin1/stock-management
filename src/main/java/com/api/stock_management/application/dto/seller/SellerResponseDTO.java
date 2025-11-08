package com.api.stock_management.application.dto.seller;

import com.api.stock_management.domain.model.Seller;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private boolean status;
    private String imagem;

    public SellerResponseDTO (Seller seller){
        this.id = seller.getId();
        this.nome = seller.getNome();
        this.email = seller.getEmail();
        this.status = seller.isEnabled();
        this.imagem = seller.getImagem();
    }
}
