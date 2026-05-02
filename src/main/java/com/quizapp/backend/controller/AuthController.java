package com.quizapp.backend.controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.quizapp.backend.model.User;
import com.quizapp.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(originPatterns = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ─── REGISTER ──────────────────────────────────────
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody Map<String, String> body) {

        String username = body.get("username");
        String email    = body.get("email");
        String password = body.get("password");

        // Basic validation
        if (username == null || username.isBlank() ||
            email    == null || email.isBlank()    ||
            password == null || password.isBlank()) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("error", "All fields are required!"));
        }

        String result = authService.register(username, email, password);

        return switch (result) {
            case "USERNAME_TAKEN" -> ResponseEntity
                .badRequest()
                .body(Map.of("error", "Username already taken!"));

            case "EMAIL_TAKEN" -> ResponseEntity
                .badRequest()
                .body(Map.of("error", "Email already registered!"));

            default -> ResponseEntity.ok(
                Map.of("message", "Registration successful!"));
        };
    }

    // ─── LOGIN ─────────────────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        // Basic validation
        if (username == null || username.isBlank() ||
            password == null || password.isBlank()) {
            return ResponseEntity
                .badRequest()
                .body(Map.of("error", "Username and password required!"));
        }

        User user = authService.login(username, password);

        if (user == null) {
            return ResponseEntity
                .status(401)
                .body(Map.of("error", "Invalid username or password!"));
        }

        // Return safe user info (never return password!)
        return ResponseEntity.ok(Map.of(
            "id",       user.getId(),
            "username", user.getUsername(),
            "email",    user.getEmail(),
            "role",     user.getRole()
        ));
    }
}
