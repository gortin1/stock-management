package com.api.stock_management.application.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequestDTO {

    @NotBlank(message = "O nome do produto não pode estar em branco.")
    private String nome;

    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser maior que zero.")
    private BigDecimal preco;

    @NotNull(message = "A quantidade não pode ser nula.")
    @PositiveOrZero(message = "A quantidade deve ser maior ou igual a zero.")
    private Integer quantidade;
}
