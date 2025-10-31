package com.api.stock_management.infrastructure.security;

import com.api.stock_management.domain.repository.SellerRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        // A sua verificação está correta
        if (token != null && !token.isBlank()) {

            try {
                // TENTAMOS validar o token
                var email = tokenService.validateToken(token);

                sellerRepository.findByEmail(email).ifPresent(seller -> {
                    var authentication = new UsernamePasswordAuthenticationToken(seller, null, seller.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });

            } catch (Exception e) {
                // SE O TOKEN FALHAR A VALIDAÇÃO (malformado, expirado, etc.)
                // Nós apanhamos o erro e não fazemos nada.
                // O SecurityContextHolder permanece vazio, e o utilizador não é autenticado.
                // Pode adicionar um log aqui se quiser: logger.warn("Invalid JWT: {}", e.getMessage());
                SecurityContextHolder.clearContext(); // Limpa o contexto por segurança
            }
        }

        // Independentemente do que acontecer, continuamos a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.substring(7);
    }
}
