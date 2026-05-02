package com.quizapp.backend.repository;

import com.quizapp.backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository 
extends JpaRepository<Question, Long> { // ✅ Long


    // ✅ Spring auto-generates this SQL:
    // SELECT * FROM questions WHERE quiz_id = ?
    List<Question> findByQuizId(Long quizId);
    void deleteByQuizId(Long quizId);
}
