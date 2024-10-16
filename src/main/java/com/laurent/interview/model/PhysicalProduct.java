package com.laurent.interview.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("PHYSICAL")
public class PhysicalProduct extends OrderItem {
    private double shippingWeight;

    public double calculateShippingCost() {
        // Simple shipping cost calculation: Rwf 1000 per kg
        return shippingWeight * 1000;
    }
}

