/**
 * 
 */
package com.fetch.rewards.receipt_processor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.rewards.receipt_processor.model.Receipt;
import com.fetch.rewards.receipt_processor.repository.ReceiptRepository;
import com.fetch.rewards.receipt_processor.service.ReceiptProcessorService;
import com.fetch.rewards.receipt_processor.service.ReceiptService;

import jakarta.validation.Valid;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
	
	  private final ReceiptService receiptService;

	    public ReceiptController(ReceiptService receiptService) {
	        this.receiptService = receiptService;
	    }
    
	    @PostMapping("/process")
	    public ResponseEntity<Map<String, Object>> processReceipt(@Valid @RequestBody Receipt receipt, BindingResult result) {
	        if (result.hasErrors()) {
	        	return ResponseEntity.badRequest().body(Collections.singletonMap("error", "The receipt is invalid."));
	        }

	        String id = receiptService.saveReceipt(receipt);
	        return ResponseEntity.ok(Collections.singletonMap("id", id));
	    }

	    @GetMapping("/{id}/points")
	    public ResponseEntity<Map<String, Object>> getPoints(@PathVariable String id) {
	        Optional<Receipt> receiptOptional = receiptService.findReceiptById(id);
	        if (receiptOptional.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(Collections.singletonMap("error", "No receipt found for that ID."));
	        }

	        int points = receiptService.calculatePoints(receiptOptional.get());
	        return ResponseEntity.ok(Collections.singletonMap("points", points));
	    }

	

	
	
}
