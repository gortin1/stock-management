package com.api.stock_management.application.controller;

import java.util.List;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.stock_management.application.service.ProductService;
import com.api.stock_management.domain.Product;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	   @Autowired
	    private ProductService productService;

	@PostMapping("/save")
	public ResponseEntity<String> ProductSave(Product data) {
		try {
			Product service = productService.createProduct(data);
			if (data == null) {
				return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("Dados invalidos...");
			}

			if (service != null) {
				return ResponseEntity.status(HttpStatus.SC_CREATED).body("Criado com sucesso!");
			} else {
				return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Rota não encontrada.");
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.body("Erro interno do servidor: " + ex.getMessage());
		}
	}

	@GetMapping("/getall")
	public ResponseEntity<String> ProductGetAll() {
		try {
			List<Product> service = productService.getallProducts();

			if (service != null) {
				return ResponseEntity.status(HttpStatus.SC_OK).body("Sucesso!");
			} else {
				return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Rota não encontrada.");
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.body("Erro interno do servidor: " + ex.getMessage());

		}
	}
	
	@GetMapping("/getby/{id}")
	public ResponseEntity<String> ProoductGetById(@PathVariable Long id){
		try {
			Product service = productService.getProduct(id);
			
			if(service != null) {
				return ResponseEntity.status(HttpStatus.SC_OK).body("Sucesso!");
			} else {
				return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Rota não encontrada.");
			}
			
			
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.body("Erro interno do servidor: " + ex.getMessage());
		}
	}
}
