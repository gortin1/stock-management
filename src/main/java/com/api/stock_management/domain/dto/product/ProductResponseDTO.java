package com.api.stock_management.domain.dto.product;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ProductResponseDTO {
    private Long id;
    private String nome;
    private Double preco;
    private Integer quantidade;
    private String status;
    private String img;
}
