package com.fetch.rewards.receipt_processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testProcessReceipt() throws Exception {
        String receiptJson = """
                {
                  "retailer": "Target",
                  "purchaseDate": "2022-01-02",
                  "purchaseTime": "13:13",
                  "total": "1.25",
                  "items": [
                    { "shortDescription": "Pepsi - 12-oz", "price": "1.25" }
                  ]
                }
                """;

        mockMvc.perform(post("/receipts/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(receiptJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void testGetPointsForValidId() throws Exception {
        String receiptJson = """
                {
                  "retailer": "Target",
                  "purchaseDate": "2022-01-02",
                  "purchaseTime": "13:13",
                  "total": "1.25",
                  "items": [
                    { "shortDescription": "Pepsi - 12-oz", "price": "1.25" }
                  ]
                }
                """;

        // First, process the receipt to get an ID
        MvcResult result = mockMvc.perform(post("/receipts/process")
                .contentType(MediaType.APPLICATION_JSON)
                .content(receiptJson))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Map<String, String> responseMap = objectMapper.readValue(response, Map.class);
        String id = responseMap.get("id");

        // Use the ID to fetch points
        mockMvc.perform(get("/receipts/" + id + "/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").exists());
    }

    @Test
    public void testGetPointsForInvalidId() throws Exception {
        mockMvc.perform(get("/receipts/invalid-id/points"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No receipt found for that ID."));
    }
}
