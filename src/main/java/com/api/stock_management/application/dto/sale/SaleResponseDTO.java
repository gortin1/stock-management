package com.api.stock_management.application.dto.sale;

import com.api.stock_management.domain.model.Sale;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class SaleResponseDTO {
    private Long id;
    private Long produtoId;
    private String produtoNome;
    private Integer quantidadeVendida;
    private BigDecimal precoNoMomentoDaVenda;
    private LocalDateTime dataDaVenda;

    public SaleResponseDTO(Sale sale) {
        this.id = sale.getId();
        this.produtoId = sale.getProduct().getId();
        this.produtoNome = sale.getProduct().getNome();
        this.quantidadeVendida = sale.getQuantidadeVendida();
        this.precoNoMomentoDaVenda = sale.getPrecoNoMomentoDaVenda();
        this.dataDaVenda = sale.getDataDaVenda();
    }
}
