package com.api.stock_management.domain;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nome;
    private int preco;
    private double quantidade;
    private boolean status;
    private String img;


    @ManyToMany
    @JoinColumn(name = "seller_id")
    private Seller seller;



}
