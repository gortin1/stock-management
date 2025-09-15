package com.api.stock_management.application.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.stock_management.application.service.SaleService;
import com.api.stock_management.domain.Sale;

@RestController
@RequestMapping("/Sale")
public class SaleController {
	
	@Autowired
	private SaleService saleService;
	
	@PostMapping("/save")
	public ResponseEntity<String> SaleSave(@RequestParam Long productId, @RequestParam Integer qtd){
		try {
			Sale service = saleService.createSale(productId, qtd);
			if (productId == null || qtd == null) {
				return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("Dados invalidos...");
			}

			if (service != null) {
				return ResponseEntity.status(HttpStatus.SC_CREATED).body("Criado com sucesso! " + service);
			} else {
				return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body("Rota não encontrada.");
			}
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
					.body("Erro interno do servidor: " + ex.getMessage());
		}
		
	}
}
