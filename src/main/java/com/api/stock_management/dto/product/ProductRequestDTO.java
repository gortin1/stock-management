package com.api.stock_management.dto.product;

import com.api.stock_management.domain.Product;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductRequestDTO {
    private long id;
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
