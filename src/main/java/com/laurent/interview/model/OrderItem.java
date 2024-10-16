package com.laurent.interview.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "item_type")
public abstract class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public double getTotalPrice() {
        return price * quantity;
    }
}

