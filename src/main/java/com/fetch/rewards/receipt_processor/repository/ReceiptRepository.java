/**
 * 
 */
package com.fetch.rewards.receipt_processor.repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.fetch.rewards.receipt_processor.model.Receipt;

/**
 * 
 */
@Repository
public class ReceiptRepository {
	private final Map<String, Receipt> receiptStore = new ConcurrentHashMap<String, Receipt>();
	
	public String save(Receipt receipt) {
		String id = UUID.randomUUID().toString();
		receiptStore.put(id, receipt);
		return id;
	}
	
	public Optional<Receipt> findById(String id){
		return Optional.ofNullable(receiptStore.get(id));
	}

}
