package com.api.stock_management.domain.repository;

import com.api.stock_management.domain.model.Product;
import com.api.stock_management.domain.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySeller(Seller seller);
}
