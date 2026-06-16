package org.example.springlab.security.web;

import lombok.RequiredArgsConstructor;
import org.example.springlab.models.User;
import org.example.springlab.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.example.springlab.security.web.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;
    @PostMapping("/login")
    public ResponseEntity<org.example.springlab.security.web.LoginResponse> login(
            @RequestBody org.example.springlab.security.web.LoginRequest loginRequest) {
        User user;
        try {
            user = authService.authenticate(loginRequest.login(), loginRequest.password());
        } catch (IllegalArgumentException e) {
            // Something cool here
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new org.example.springlab.security.web.LoginResponse(token));
    }
}
