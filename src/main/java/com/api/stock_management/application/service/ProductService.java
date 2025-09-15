package com.api.stock_management.application.service;

import com.api.stock_management.domain.Product;
import com.api.stock_management.domain.repository.IProduct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class ProductService{

    @Autowired
    private IProduct iProduct;

    public Product createProduct(Product product){
        return iProduct.save(product);
    }
    public List<Product> getallProducts(){
        return iProduct.findAll();
    }
    public Product getProduct(Long id){
        return iProduct.findById(id).orElseThrow(()-> new RuntimeException("Produto não encontrado"));
    }
    public Product updateProduct(Long id, Product product){
        product.setId(id);
        return iProduct.save(product);
    }
    public void inactiveProduct(Long id){
        Product product = getProduct(id);
        product.setStatus("Inativo");
        iProduct.save(product);
    }


}
