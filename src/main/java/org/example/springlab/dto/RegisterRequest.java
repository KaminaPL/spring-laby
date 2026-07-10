package org.example.springlab.dto;

public record RegisterRequest(
        String login,
        String password,
        String repeatedPassword,
        String address
) {
}
