package com.api.stock_management.domain.repository;

import com.api.stock_management.domain.Sale;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ISale extends JpaRepository<Sale, Long> {
    Sale save (Sale sale);
    List<Sale> findAll();
    Optional<Sale> findById(Long id);
}
