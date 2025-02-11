/**
 * 
 */
package com.fetch.rewards.receipt_processor.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
		
		//One point for every alphanumeric character in the retailer name.
		points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();
		
		double total = Double.parseDouble(receipt.getTotal());
		
		//50 points if the total is a round dollar amount with no cents.
		if(total % 1 == 0) {
			points += 50;
		}
		
		//25 points if the total is a multiple of 0.25
		if(total % 0.25 == 0) {
			points += 25;
		}
		
		//5 points for every two items on the receipt.
		points += (receipt.getItems().size()/2)*5;
		
		//If the trimmed length of the item description is a multiple of 3, multiply the price by 0.2 and round up to the nearest integer. 
		//The result is the number of points earned.
		for(Item item : receipt.getItems()) {
			String trimmedDescription = item.getShortDescription().trim();
			if(trimmedDescription.length() % 3 ==0) {
				points += Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
			}
		}
		
		//6 points if the day in the purchase date is odd.
		int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
		if(day % 2 == 1) {
			points += 6;
		}
		
		//10 points if the time of purchase is after 2:00pm and before 4:00pm.
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime(), timeFormatter);

        LocalTime startTime = LocalTime.of(14, 0); 
        LocalTime endTime = LocalTime.of(16, 0);   

        if (purchaseTime.isAfter(startTime) && purchaseTime.isBefore(endTime)) {
            points += 10;
        }
		
		
		return points;
	}
}
