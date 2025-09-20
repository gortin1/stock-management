package com.api.stock_management.domain.dto.seller;


import com.api.stock_management.domain.Seller;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SellerResponseDTO {
    private Long id;
    private String nome;
    private String email;

    public SellerResponseDTO (Seller seller){
        this.id = seller.getId();
        this.nome = seller.getNome();
        this.email = seller.getEmail();

    }
}
