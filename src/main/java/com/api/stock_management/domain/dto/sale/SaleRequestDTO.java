package com.api.stock_management.domain.dto.sale;


import com.api.stock_management.domain.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SaleRequestDTO {

    private Long id;

    @NotBlank(message = "Produto não pode ser Nulo")
    private Product product;

    @Positive(message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    public SaleRequestDTO () {}
    public SaleRequestDTO ( Long id, Product product, Integer quantidade){
        this.id = id;
        this.product = product;
        this.quantidade = quantidade;

    }

}


























