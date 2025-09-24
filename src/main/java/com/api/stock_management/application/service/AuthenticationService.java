package com.api.stock_management.application.service;

import com.api.stock_management.application.dto.auth.TokenResponseDTO;
import com.api.stock_management.application.dto.login.LoginRequestDTO;
import com.api.stock_management.domain.model.Seller;
import com.api.stock_management.infrastructure.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    public TokenResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getEmail(),
                loginRequestDTO.getSenha()
        );

        var auth = this.authenticationManager.authenticate(usernamePassword);
        var seller = (Seller) auth.getPrincipal();
        String token = tokenService.generateToken(seller);

        return new TokenResponseDTO(token);
    }
}
