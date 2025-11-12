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
    @Column(nullable = false)
    private StatusProduct statusProduto;

    @Column(length = 1024)
    private String imagem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @PrePersist
    @PreUpdate
    public void atualizarStatus() {
        if (this.quantidade <= 0) {
            this.statusProduto = StatusProduct.EM_FALTA;
            return;
        }

        if (this.statusProduto == StatusProduct.EM_FALTA || this.statusProduto == null) {
            this.statusProduto = StatusProduct.ATIVO;
        }
    }
}
