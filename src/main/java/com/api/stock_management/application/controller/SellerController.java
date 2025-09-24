package com.api.stock_management.application.controller;

import com.api.stock_management.application.dto.seller.SellerActivateRequestDTO;
import com.api.stock_management.application.dto.seller.SellerRequestDTO;
import com.api.stock_management.application.dto.seller.SellerResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.api.stock_management.application.service.SellerService;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {
	
	@Autowired
	private SellerService sellerService;

	@PostMapping
	public ResponseEntity<SellerResponseDTO> createSeller(@RequestBody @Valid SellerRequestDTO sellerRequest) {
		SellerResponseDTO sellerResponse = sellerService.createSeller(sellerRequest);

		return ResponseEntity.ok(sellerResponse);
	}

	@PostMapping("/activate")
	public ResponseEntity<SellerResponseDTO> activateSeller(@RequestBody @Valid SellerActivateRequestDTO sellerActivateRequest) {
		SellerResponseDTO sellerResponse = sellerService.activateSeller(sellerActivateRequest);

		return ResponseEntity.ok(sellerResponse);
	}
}
