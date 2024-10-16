package com.laurent.interview.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        private String type; // PHYSICAL, DIGITAL, GIFT_CARD
        private String productName;
        private double price;
        private int quantity;
        private Double shippingWeight; // for physical products
        private String recipientEmail; // for gift cards
    }
}
