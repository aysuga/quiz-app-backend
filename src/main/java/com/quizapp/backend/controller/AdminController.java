package com.quizapp.backend.controller;

import com.quizapp.backend.model.Question;
import com.quizapp.backend.model.Quiz;
import com.quizapp.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(originPatterns = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // ── CREATE a new quiz ────────────────────────────
    @PostMapping("/quiz/create")
    public ResponseEntity<?> createQuiz(
            @RequestBody Quiz quiz) {

        System.out.println(">>> Admin: createQuiz → " + quiz.getTitle());
        Quiz created = adminService.createQuiz(quiz);

        return ResponseEntity.ok(Map.of(
            "message", "Quiz created successfully! ✅",
            "quizId",  created.getId(),
            "title",   created.getTitle()
        ));
    }

    // ── GET all quizzes (for admin list view) ────────
    @GetMapping("/quiz/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(adminService.getAllQuizzes());
    }

    // ── DELETE a quiz ────────────────────────────────
    @DeleteMapping("/quiz/{quizId}")
    public ResponseEntity<?> deleteQuiz(
            @PathVariable Long quizId) {

        System.out.println(">>> Admin: deleteQuiz → " + quizId);
        adminService.deleteQuiz(quizId);

        return ResponseEntity.ok(Map.of(
            "message", "Quiz deleted successfully! 🗑️"
        ));
    }

    // ── ADD a question to a quiz ─────────────────────
    @PostMapping("/quiz/{quizId}/add-question")
    public ResponseEntity<?> addQuestion(
            @PathVariable Long quizId,
            @RequestBody Question question) {

        System.out.println(">>> Admin: addQuestion to quizId=" + quizId);
        question.setQuizId(quizId); // ✅ link question to quiz
        Question saved = adminService.addQuestion(question);

        return ResponseEntity.ok(Map.of(
            "message",    "Question added successfully! ✅",
            "questionId", saved.getId()
        ));
    }

    // ── GET all questions for a quiz ─────────────────
    @GetMapping("/quiz/{quizId}/questions")
    public ResponseEntity<List<Question>> getQuestions(
            @PathVariable Long quizId) {
        return ResponseEntity.ok(
                adminService.getQuestionsByQuizId(quizId));
    }

    // ── DELETE a question ────────────────────────────
    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<?> deleteQuestion(
            @PathVariable Long questionId) {

        System.out.println(">>> Admin: deleteQuestion → " + questionId);
        adminService.deleteQuestion(questionId);

        return ResponseEntity.ok(Map.of(
            "message", "Question deleted successfully! 🗑️"
        ));
    }
}
