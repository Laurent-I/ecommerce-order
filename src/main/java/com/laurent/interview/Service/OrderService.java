package com.laurent.interview.Service;

import com.laurent.interview.dto.OrderRequest;
import com.laurent.interview.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrder(Long id);
}
