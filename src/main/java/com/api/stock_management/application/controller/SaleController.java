package com.api.stock_management.application.controller;

import com.api.stock_management.application.dto.sale.SaleRequestDTO;
import com.api.stock_management.application.dto.sale.SaleResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.stock_management.application.service.SaleService;

@RestController
@RequestMapping("/api/sales")
public class SaleController {
	
	@Autowired
	private SaleService saleService;
	
	@PostMapping
	public ResponseEntity<SaleResponseDTO> createSale(@RequestBody @Valid SaleRequestDTO saleRequest){
		SaleResponseDTO saleResponse = saleService.createSale(saleRequest);

		return ResponseEntity.ok(saleResponse);
	}
}
