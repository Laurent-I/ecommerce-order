package com.laurent.interview.Service;

import com.laurent.interview.dto.OrderRequest;
import com.laurent.interview.dto.OrderResponse;
import com.laurent.interview.model.*;
import com.laurent.interview.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order();

        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            OrderItem item = createOrderItem(itemRequest);
            order.addItem(item);
        }

        order = orderRepository.save(order);
        return createOrderResponse(order);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return createOrderResponse(order);
    }

    private OrderItem createOrderItem(OrderRequest.OrderItemRequest request) {
        OrderItem item;

        switch (request.getType()) {
            case "PHYSICAL":
                PhysicalProduct physical = new PhysicalProduct();
                physical.setShippingWeight(request.getShippingWeight());
                item = physical;
                break;

            case "DIGITAL":
                item = new DigitalProduct();
                break;

            case "GIFT_CARD":
                GiftCard giftCard = new GiftCard();
                giftCard.setRecipientEmail(request.getRecipientEmail());
                item = giftCard;
                break;

            default:
                throw new IllegalArgumentException("Invalid item type: " + request.getType());
        }

        item.setProductName(request.getProductName());
        item.setPrice(request.getPrice());
        item.setQuantity(request.getQuantity());

        return item;
    }

    private OrderResponse createOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getId());

        List<OrderResponse.OrderItemResponse> items = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            OrderResponse.OrderItemResponse itemResponse = new OrderResponse.OrderItemResponse();
            itemResponse.setProductName(item.getProductName());
            itemResponse.setPrice(item.getPrice());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setTotalPrice(item.getTotalPrice());

            if (item instanceof PhysicalProduct) {
                itemResponse.setType("PHYSICAL");
                itemResponse.setShippingCost(((PhysicalProduct) item).calculateShippingCost());
            } else if (item instanceof DigitalProduct) {
                itemResponse.setType("DIGITAL");
                itemResponse.setDownloadLink(((DigitalProduct) item).generateDownloadLink());
            } else if (item instanceof GiftCard) {
                itemResponse.setType("GIFT_CARD");
                ((GiftCard) item).sendGift();
            }

            items.add(itemResponse);
        }

        response.setItems(items);
        response.setTotalPrice(order.getTotalPrice());
        response.setTotalShippingCost(order.getTotalShippingCost());
        response.setGrandTotal(order.getTotalPrice() + order.getTotalShippingCost());

        return response;
    }
}
