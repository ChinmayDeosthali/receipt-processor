/**
 * 
 */
package com.fetch.rewards.receipt_processor.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {
    @NotBlank(message = "Retailer name is required")
    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "Retailer name contains invalid characters")
    private String retailer;

    @NotBlank(message = "Purchase date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Invalid date format, expected YYYY-MM-DD")
    private String purchaseDate;

    @NotBlank(message = "Purchase time is required")
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Invalid time format, expected HH:MM")
    private String purchaseTime;

    @NotBlank(message = "Total amount is required")
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be a decimal with two places (e.g., 6.49)")
    private String total;

    @Size(min = 1, message = "At least one item is required")
    private List<Item> items;

}
