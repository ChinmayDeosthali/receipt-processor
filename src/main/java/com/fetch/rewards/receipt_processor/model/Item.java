/**
 * 
 */
package com.fetch.rewards.receipt_processor.model;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	@Pattern(regexp = "^[\\w\\s\\-]+$", message = "Short description contains invalid characters")
	private String shortDescription;

	@Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Price must be a decimal with two places (e.g., 6.49)")
	private String price;

}
