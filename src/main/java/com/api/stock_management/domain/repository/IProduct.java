package com.api.stock_management.domain.repository;


import com.api.stock_management.domain.Product;
import org.hibernate.annotations.processing.Find;

import java.util.List;
import java.util.Optional;

public interface IProduct {

    Product save(Product product);
    Optional <Product> FindById(Long id);
    List<Product> FindAll();



}
