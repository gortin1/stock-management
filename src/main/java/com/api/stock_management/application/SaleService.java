package com.api.stock_management.application;


import com.api.stock_management.domain.Product;
import com.api.stock_management.domain.Sale;
import com.api.stock_management.domain.repository.IProduct;
import com.api.stock_management.domain.repository.ISale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    @Autowired
    private ISale iSale;

    @Autowired
    private IProduct iProduct;

    public Sale createSale (Long productId, Integer quantidade){
        Product product = iProduct.FindById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (!"Ativo".equalsIgnoreCase(product.getStatus())) {
            throw new RuntimeException("Produto inativo, não pode ser vendido");
        }

        if (product.getQuantidade() < quantidade ) {
            throw new RuntimeException("Quantidade em estoque insuficiente");
        }

        product.setQuantidade(product.getQuantidade() - quantidade);
        iProduct.save((product));

        Sale sale = new Sale();
        sale.setProduct(product);
        sale.setQuantidade(quantidade);

        return iSale.save(sale);
    }
}

