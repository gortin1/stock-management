package com.api.stock_management.domain.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    private Long id;

    @NotBlank(message = "O nome do produto não pode estar em branco")
    private String nome;

    @NotNull(message = "O preço não pode ser nulo")
    @Positive(message = "O preço deve ser maior que zero")
    private Double preco;

    @Positive(message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    private String status;
    private String img;


    public ProductRequestDTO() {
    }

    public ProductRequestDTO(Long id, String nome, Double preco, Integer quantidade, String status, String img) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.status = status;
        this.img = img;
    }
}
