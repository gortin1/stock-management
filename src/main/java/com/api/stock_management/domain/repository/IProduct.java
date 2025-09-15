package com.api.stock_management.domain.repository;


import com.api.stock_management.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProduct extends JpaRepository<Product, Long> {

 
	Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();

}