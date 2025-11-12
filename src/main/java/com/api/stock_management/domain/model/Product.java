package com.api.stock_management.domain.model;

import com.api.stock_management.application.dto.product.StatusProduct;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity(name = "tb_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private BigDecimal preco;

    @Column(nullable = false)
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    private StatusProduct statusProduto;

    @PrePersist
    @PreUpdate
    public void atualizarStatus() {
        if (quantidade <= 0) {
            statusProduto = StatusProduct.EM_FALTA;
        } else {
            if (this.statusProduto == null || this.statusProduto == StatusProduct.EM_FALTA) {
                statusProduto = StatusProduct.ATIVO;
            }
        }
    }
    @Column
    private String imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;
}
