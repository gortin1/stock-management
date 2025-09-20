package com.api.stock_management.domain.dto.product;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequestDTO {
    private Long id;
    private String nome;
    private Double preco;
    private Integer quantidade;
    private String status;
    private String img;

    public ProductRequestDTO() {}
    public ProductRequestDTO(Long id, String nome, Double preco, Integer quantidade, String status,String img) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.img = img;
        this.quantidade = quantidade;
        this.status = status;
    }

}
