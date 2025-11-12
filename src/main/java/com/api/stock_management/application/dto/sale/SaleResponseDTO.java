package com.api.stock_management.application.dto.sale;

import com.api.stock_management.domain.model.Pedido;
import com.api.stock_management.domain.model.Sale;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SaleResponseDTO {
    private Long id;
    private Long pedidoId;
    private Long produtoId;
    private String produtoNome;
    private Integer quantidadeVendida;
    private BigDecimal precoNoMomentoDaVenda;
    private LocalDateTime dataPedido;

    public SaleResponseDTO(Sale sale, Pedido pedido) {
        this.id = sale.getId();
        this.pedidoId = sale.getPedido().getId();
        this.produtoId = sale.getProduct().getId();
        this.produtoNome = sale.getProduct().getNome();
        this.quantidadeVendida = sale.getQuantidadeVendida();
        this.precoNoMomentoDaVenda = sale.getPrecoNoMomentoDaVenda();
        this.dataPedido = pedido.getDataPedido();
    }
}
