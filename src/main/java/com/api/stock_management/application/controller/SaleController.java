package com.api.stock_management.application.controller;

import com.api.stock_management.application.dto.sale.SaleRequestDTO;
import com.api.stock_management.application.dto.sale.SaleResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.stock_management.application.service.SaleService;

@RestController
@RequestMapping("/api/sales")
public class SaleController {
	
	@Autowired
	private SaleService saleService;

	@GetMapping
	public ResponseEntity<List<SaleResponseDTO>> getAllSales() {
		List<SaleResponseDTO> sales = saleService.getAllSalesBySeller();
		return ResponseEntity.ok(sales);
	}

	@PostMapping
	public ResponseEntity<?> createSale(@RequestBody @Valid SaleRequestDTO saleRequest) {
		try {
			List<SaleResponseDTO> saleResponse = saleService.createSale(saleRequest);
			return ResponseEntity.ok(saleResponse);
		} catch (IllegalStateException | SecurityException | EntityNotFoundException ex) {
			return new ResponseEntity<>(
					Map.of("message", ex.getMessage()),
					HttpStatus.BAD_REQUEST
			);
		}
	}
}
