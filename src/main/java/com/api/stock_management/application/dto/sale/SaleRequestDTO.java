package com.api.stock_management.application.dto.sale;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaleRequestDTO {

    @NotEmpty(message = "A lista de itens não pode estar vazia.")
    @Valid
    private List<SaleItemRequestDTO> items;
}
