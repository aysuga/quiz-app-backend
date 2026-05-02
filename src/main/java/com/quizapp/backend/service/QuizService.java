package com.quizapp.backend.service;

import com.quizapp.backend.model.Question;
import com.quizapp.backend.model.Quiz;
import com.quizapp.backend.repository.QuestionRepository;
import com.quizapp.backend.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // ── All quizzes ──────────────────────────────────
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // ── Single quiz ──────────────────────────────────
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
            .orElseThrow(() ->
                new RuntimeException("Quiz not found: " + id));
    }

    // ── Questions for a quiz ─────────────────────────
    public List<Question> getQuestionsByQuizId(Long quizId) {
        List<Question> q =
                questionRepository.findByQuizId(quizId);
        System.out.println(
            "QuizService: found " + q.size() +
            " questions for quizId=" + quizId);
        return q;
    }
}
