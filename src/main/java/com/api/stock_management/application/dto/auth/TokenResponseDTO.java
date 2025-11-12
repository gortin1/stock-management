package com.api.stock_management.application.dto.auth;

import com.api.stock_management.application.dto.seller.SellerResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDTO {

    private String token;
    private SellerResponseDTO user;


    public TokenResponseDTO(String token, SellerResponseDTO user) {
        this.token = token;
        this.user = user;
    }
}