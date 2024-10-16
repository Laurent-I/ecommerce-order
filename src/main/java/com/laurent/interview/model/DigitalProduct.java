package com.laurent.interview.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("DIGITAL")
public class DigitalProduct extends OrderItem {
    public String generateDownloadLink() {
        return "https://download.example.com/" + getId();
    }
}

