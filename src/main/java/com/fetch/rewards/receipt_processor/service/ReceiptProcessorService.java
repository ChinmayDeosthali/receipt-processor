/**
 * 
 */
package com.fetch.rewards.receipt_processor.service;

import org.springframework.stereotype.Service;

import com.fetch.rewards.receipt_processor.model.Item;
import com.fetch.rewards.receipt_processor.model.Receipt;

/**
 * 
 */
@Service
public class ReceiptProcessorService {

	public int calculatePoints(Receipt receipt) {
		int points = 0;
		
		points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();
		
		double total = Double.parseDouble(receipt.getTotal());
		if(total % 1 == 0) {
			points += 50;
		}
		
		if(total % 0.25 == 0) {
			points += 25;
		}
		
		points += (receipt.getItems().size()/2)*5;
		
		for(Item item : receipt.getItems()) {
			String trimmedDescription = item.getShortDescription().trim();
			if(trimmedDescription.length() % 3 ==0) {
				points += Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
			}
		}
		
		int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
		if(day % 2 == 1) {
			points += 6;
		}
		
		int hour = Integer.parseInt(receipt.getPurchaseTime().split(":")[0]);
		if(hour > 14 && hour < 16) {
			points += 10;
		}
		
		
		return points;
	}
}
