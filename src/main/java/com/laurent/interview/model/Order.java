package com.laurent.interview.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(OrderItem::getTotalPrice)
                .sum();
    }

    public double getTotalShippingCost() {
        return items.stream()
                .filter(item -> item instanceof PhysicalProduct)
                .map(item -> (PhysicalProduct) item)
                .mapToDouble(PhysicalProduct::calculateShippingCost)
                .sum();
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}