package com.api.stock_management.domain;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Double preco;
    private Integer quantidade;
    private String status;
    private String img;


    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;



}
