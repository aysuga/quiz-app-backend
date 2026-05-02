package com.quizapp.backend.service;

import com.quizapp.backend.model.Question;
import com.quizapp.backend.model.Quiz;
import com.quizapp.backend.repository.QuestionRepository;
import com.quizapp.backend.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    // ── Create quiz ──────────────────────────────────
    public Quiz createQuiz(Quiz quiz) {
        quiz.setCreatedAt(LocalDateTime.now());
        Quiz saved = quizRepository.save(quiz);
        System.out.println(">>> Quiz saved with ID: " + saved.getId());
        return saved;
    }

    // ── Get all quizzes ──────────────────────────────
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // ── Delete quiz (also deletes its questions) ─────
    public void deleteQuiz(Long quizId) {
        // ✅ delete questions first to avoid FK constraint error
        questionRepository.deleteByQuizId(quizId);
        quizRepository.deleteById(quizId);
        System.out.println(">>> Quiz " + quizId + " deleted");
    }

    // ── Add question to quiz ─────────────────────────
    public Question addQuestion(Question question) {
        Question saved = questionRepository.save(question);
        System.out.println(">>> Question saved with ID: " + saved.getId());
        return saved;
    }

    // ── Get questions by quiz ────────────────────────
    public List<Question> getQuestionsByQuizId(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    // ── Delete question ──────────────────────────────
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteById(questionId);
        System.out.println(">>> Question " + questionId + " deleted");
    }
}
