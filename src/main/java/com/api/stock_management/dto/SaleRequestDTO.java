package com.api.stock_management.dto;


import com.api.stock_management.domain.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SaleRequestDTO {

    private Long id;
    private Product product;
    private Integer quantidade;

    public SaleRequestDTO () {}
    public SaleRequestDTO ( Long id, Product product, Integer quantidade){

        this.id = id;
        this.product = product;
        this.quantidade = quantidade;

    }

}




























