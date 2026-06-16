package org.example.springlab.dto;

public record LoginRequest(
        String login,
        String password
) {
}
