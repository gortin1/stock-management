package com.api.stock_management.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "tb_sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private Integer quantidadeVendida;

    @Column(nullable = false)
    private BigDecimal precoNoMomentoDaVenda;

    @Column(name = "data_da_venda", nullable = false)
    private LocalDateTime dataDaVenda;
}
