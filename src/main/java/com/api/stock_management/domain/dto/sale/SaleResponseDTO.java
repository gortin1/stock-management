package com.api.stock_management.domain.dto.sale;

import com.api.stock_management.domain.Product;
import com.api.stock_management.domain.Sale;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaleResponseDTO {
    private Long id;
    private Integer quantidade;
    private Product product;

    public SaleResponseDTO(Sale sale) {
        this.id = sale.getId();
        this.quantidade = sale.getQuantidade();
        this.product = sale.getProduct();
    }
}
