/**
 * 
 */
package com.fetch.rewards.receipt_processor.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fetch.rewards.receipt_processor.model.Receipt;
import com.fetch.rewards.receipt_processor.repository.ReceiptRepository;

/**
 * 
 */
@Service
public class ReceiptService {

    private final ReceiptRepository receiptRepository;
    private final ReceiptProcessorService receiptProcessorService;

    public ReceiptService(ReceiptRepository receiptRepository, ReceiptProcessorService receiptProcessorService) {
        this.receiptRepository = receiptRepository;
        this.receiptProcessorService = receiptProcessorService;
    }

    public String saveReceipt(Receipt receipt) {
        return receiptRepository.save(receipt);
    }

    public Optional<Receipt> findReceiptById(String id) {
        return receiptRepository.findById(id);
    }

    public int calculatePoints(Receipt receipt) {
        return receiptProcessorService.calculatePoints(receipt);
    }
}

