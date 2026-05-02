package com.quizapp.backend.repository;       

import com.quizapp.backend.model.Quiz;          
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository 
extends JpaRepository<Quiz, Long> { // ✅ Long

    // your methods here
}
