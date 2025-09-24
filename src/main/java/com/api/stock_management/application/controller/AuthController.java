package com.api.stock_management.application.controller;

import com.api.stock_management.application.dto.auth.TokenResponseDTO;
import com.api.stock_management.application.dto.login.LoginRequestDTO;
import com.api.stock_management.application.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequest) {
        TokenResponseDTO tokenResponse = authenticationService.login(loginRequest);

        return ResponseEntity.ok(tokenResponse);
    }
}
