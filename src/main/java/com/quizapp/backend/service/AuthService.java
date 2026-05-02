package com.quizapp.backend.service;

import com.quizapp.backend.model.User;
import com.quizapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // ─── REGISTER ──────────────────────────────────────
    public String register(String username,
                           String email,
                           String password) {

        // 1. Check if username already taken
        if (userRepository.existsByUsername(username)) {
            return "USERNAME_TAKEN";
        }

        // 2. Check if email already taken
        if (userRepository.existsByEmail(email)) {
            return "EMAIL_TAKEN";
        }

        // 3. Create new user and SAVE to DB
        // ⚠️ NOTE: We store plain password for now (no BCrypt)
        // This keeps it simple — you can add BCrypt later
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);   // plain text for now
        user.setRole("USER");

        userRepository.save(user);    // ✅ THIS actually saves to DB!

        return "SUCCESS";
    }

    // ─── LOGIN ─────────────────────────────────────────
    public User login(String username, String password) {

        // 1. Find user by username
        Optional<User> optionalUser =
            userRepository.findByUsername(username);

        // 2. If user not found → return null
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        // 3. Compare plain text passwords
        if (!user.getPassword().equals(password)) {
            return null;
        }

        // 4. Success — return the user object
        return user;
    }
}
