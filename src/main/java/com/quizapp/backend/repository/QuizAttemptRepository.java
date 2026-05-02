package com.quizapp.backend.repository;

import com.quizapp.backend.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAttemptRepository
        extends JpaRepository<QuizAttempt, Long> {

    // ✅ Uses Long — matches QuizAttempt.quizId field type
    List<QuizAttempt> findByQuizIdOrderByScoreDesc(long quizId);
}
