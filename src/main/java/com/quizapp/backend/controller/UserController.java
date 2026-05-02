package com.quizapp.backend.controller;

import com.quizapp.backend.model.User;
import com.quizapp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(originPatterns = "*")
public class UserController {

    @Autowired
    private UserService userService;

    // ── REGISTER ──────────────────────────────────────────
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            String result = userService.register(user); // ✅ String return

            if (result.equals("SUCCESS")) {
                return ResponseEntity.ok("Registered successfully! 🎉");

            } else if (result.equals("USERNAME_TAKEN")) {
                return ResponseEntity.badRequest().body("❌ Username already taken!");

            } else if (result.equals("EMAIL_TAKEN")) {
                return ResponseEntity.badRequest().body("❌ Email already registered!");

            } else {
                return ResponseEntity.badRequest().body("❌ Registration failed!");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Error: " + e.getMessage());
        }
    }

    // ── LOGIN ──────────────────────────────────────────────
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            User user = userService.login(username, password);

            if (user != null) {
                return ResponseEntity.ok(user); // ✅ Returns User object (no password)
            } else {
                return ResponseEntity.badRequest().body("❌ Invalid username or password!");
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("❌ Error: " + e.getMessage());
        }
    }

    // ── GET ALL USERS ──────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
