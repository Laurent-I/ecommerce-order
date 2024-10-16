package com.laurent.interview.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderResponse {
    private Long orderId;
    private List<OrderItemResponse> items;
    private double totalPrice;
    private double totalShippingCost;
    private double grandTotal;

    @Data
    public static class OrderItemResponse {
        private String type;
        private String productName;
        private double price;
        private int quantity;
        private double totalPrice;
        private Double shippingCost;
        private String downloadLink;
    }
}
