package org.example.springlab.dto;

public record ChargeRequest(
        double amount,
        String description,
        String stripeEmail,
        String stripeToken
) {
}
