package com.api.stock_management.domain.repository;

import com.api.stock_management.domain.Sale;

import java.util.List;
import java.util.Optional;

public interface ISale {
    Sale save (Sale sale);
    List<Sale> FindAll();
    Optional<Sale> FindById(Long id);
}
