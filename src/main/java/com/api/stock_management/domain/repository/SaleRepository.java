package com.api.stock_management.domain.repository;

import com.api.stock_management.domain.model.Sale;
import com.api.stock_management.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findBySeller(Seller seller);
}
