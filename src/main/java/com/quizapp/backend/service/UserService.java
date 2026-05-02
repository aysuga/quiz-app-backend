package com.quizapp.backend.service;

import com.quizapp.backend.model.User;
import com.quizapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // ── REGISTER ──────────────────────────────────────
    public String register(User user) {

        // ✅ Check if username already taken
        if (userRepository.existsByUsername(user.getUsername())) {
            return "USERNAME_TAKEN";
        }

        // ✅ Check if email already taken
        if (userRepository.existsByEmail(user.getEmail())) {
            return "EMAIL_TAKEN";
        }

        // ✅ Save the user (plain password for now)
        user.setRole("USER");
        userRepository.save(user);

        // ✅ MUST return exactly "SUCCESS"
        // Frontend checks for this exact string!
        return "SUCCESS";
    }

    // ── LOGIN ─────────────────────────────────────────
    public User login(String username, String password) {

        Optional<User> userOpt =
            userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // ✅ Plain password comparison
            if (user.getPassword().equals(password)) {
                // ✅ Never send password back to frontend!
                user.setPassword(null);
                return user;
            }
        }

        // ✅ Return null = invalid credentials
        return null;
    }

	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}
}
