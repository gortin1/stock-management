package com.api.stock_management.domain.repository;

import com.api.stock_management.domain.Seller;
import java.util.List;
import java.util.Optional;

public interface ISeller {

    Seller save (Seller seller);
    List<Seller> FindAll();
    Optional<Seller> FindByEmail(String email);
    Optional<Seller> FindByCelular (String celular);



}
