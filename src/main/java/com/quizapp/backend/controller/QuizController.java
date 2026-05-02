package com.quizapp.backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.quizapp.backend.model.LeaderboardDTO;
import com.quizapp.backend.model.Question;
import com.quizapp.backend.model.Quiz;
import com.quizapp.backend.service.QuizService;
import com.quizapp.backend.service.QuizAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
@CrossOrigin(originPatterns = "*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizAttemptService quizAttemptService;

    // ── GET all quizzes ──────────────────────────────
    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    // ── GET single quiz by ID ────────────────────────
    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuizById(
            @PathVariable Long quizId) {
        return ResponseEntity.ok(
                quizService.getQuizById(quizId));
    }

    // ── GET questions for a quiz ─────────────────────
    @GetMapping("/{quizId}/questions")
    public ResponseEntity<?> getQuestions(
            @PathVariable Long quizId) {

        System.out.println(
            ">>> getQuestions called for quizId: " + quizId);

        List<Question> questions =
                quizService.getQuestionsByQuizId(quizId);

        System.out.println(
            ">>> Found " + questions.size() + " questions");

        if (questions.isEmpty()) {
            return ResponseEntity.ok(
                Map.of("message",
                    "No questions found for quiz " + quizId));
        }

        return ResponseEntity.ok(questions);
    }

    // ── POST submit quiz ─────────────────────────────
    @PostMapping("/{quizId}/submit")
    public ResponseEntity<?> submitQuiz(
            @PathVariable Long quizId,
            @RequestParam Long userId,
            @RequestBody Map<String, String> answers) { // ✅ String keys — matches JSON!

        System.out.println(">>> submitQuiz called | quizId="
            + quizId + " | userId=" + userId);
        System.out.println(">>> Answers received: " + answers); // debug

        int score = quizAttemptService
                        .submitQuiz(quizId, userId, answers);

        System.out.println(">>> Score returned to frontend: " + score);

        return ResponseEntity.ok(Map.of(
            "score",   score,
            "message", "Quiz submitted successfully! 🎉"
        ));
    }

    // ── GET leaderboard ──────────────────────────────
    @GetMapping("/{quizId}/leaderboard")
    public ResponseEntity<?> getLeaderboard(
            @PathVariable Long quizId) {

        List<LeaderboardDTO> leaderboard =
                quizAttemptService.getLeaderboard(quizId);

        return ResponseEntity.ok(leaderboard);
    }
}
