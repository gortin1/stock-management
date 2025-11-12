package com.api.stock_management.domain.repository;

import com.api.stock_management.domain.model.Sale;
import com.api.stock_management.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT s FROM tb_sale s WHERE s.pedido.seller = :seller")
    List<Sale> findAllBySeller(@Param("seller") Seller seller);
}
