package com.api.stock_management.application.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.stock_management.application.service.SellerService;
import com.api.stock_management.domain.Seller;

@RestController
@RequestMapping("/seller")
public class SellerController {
	
	@Autowired
	private SellerService sellerService;
	
	@PostMapping("/save")
	public ResponseEntity<String> SellerSave(@RequestParam Seller data){
		try {
			Seller service = sellerService.createSeller(data);
			if (data == null) {
				return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("Dados invalidos...");
			}

			if (service != null) {
				return ResponseEntity.status(HttpStatus.SC_CREATED).body("Criado com sucesso! " + data);
			} else {
				return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Rota não encontrada.");
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.body("Erro interno do servidor: " + ex.getMessage());
		}
	}

}
