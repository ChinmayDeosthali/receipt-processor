/**
 * 
 */
package com.fetch.rewards.receipt_processor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.rewards.receipt_processor.model.Receipt;
import com.fetch.rewards.receipt_processor.repository.ReceiptRepository;
import com.fetch.rewards.receipt_processor.service.ReceiptProcessorService;

import jakarta.validation.Valid;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * 
 */
@RestController
@RequestMapping("/receipts")
public class ReceiptController {
	
	private final ReceiptRepository receiptRepository;
	private final ReceiptProcessorService receiptProcessorService;
	
    public ReceiptController(ReceiptRepository receiptRepository, ReceiptProcessorService receiptProcessorService) {
        this.receiptRepository = receiptRepository;
        this.receiptProcessorService = receiptProcessorService;
    }
	
    @PostMapping("/process")
    public ResponseEntity<?> processReceipt(@Valid @RequestBody Receipt receipt, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "The receipt is invalid."));
        }

        String id = receiptRepository.save(receipt);
        return ResponseEntity.ok(Collections.singletonMap("id", id));
    }
	
	
	@GetMapping("/{id}/points")
	public ResponseEntity<?> getPoints(@PathVariable String id) {
	    Receipt receipt = receiptRepository.findById(id).orElse(null);

	    if (receipt == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(Collections.singletonMap("error", "No receipt found for that ID."));
	    }

	    int points = receiptProcessorService.calculatePoints(receipt);
	    return ResponseEntity.ok(Collections.singletonMap("points", points));
	}

	

	
	
}
