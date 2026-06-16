package org.example.springlab.security;

public record LoginRequest(
        String login,
        String password
) {
}
