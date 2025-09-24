package com.api.stock_management.application.dto.product;

import com.api.stock_management.domain.model.Product;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponseDTO {
    private Long id;
    private String nome;
    private BigDecimal preco;
    private Integer quantidade;
    private boolean status;
    private String imagem;
    private Long sellerId;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.nome = product.getNome();
        this.preco = product.getPreco();
        this.quantidade = product.getQuantidade();
        this.status = product.isStatus();
        this.imagem = product.getImagem();
        this.sellerId = product.getSeller().getId();
    }
}
