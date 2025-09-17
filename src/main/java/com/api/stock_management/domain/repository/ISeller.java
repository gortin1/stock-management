package com.api.stock_management.domain.repository;

import com.api.stock_management.domain.Seller;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ISeller extends JpaRepository<Seller, Long> {

    Seller save (Seller seller);
    List<Seller> findAll();
    Optional<Seller> findByEmail(String email);
    Optional<Seller> findByCelular (String celular);
}
