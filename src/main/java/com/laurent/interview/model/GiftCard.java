package com.laurent.interview.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("GIFT_CARD")
public class GiftCard extends OrderItem {
    private String recipientEmail;

    public void sendGift() {
        // Simulate sending gift card via email
        System.out.println("Sending gift card to: " + recipientEmail);
    }
}

