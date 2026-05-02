package com.quizapp.backend.service;

import com.quizapp.backend.model.LeaderboardDTO;
import com.quizapp.backend.model.Question;
import com.quizapp.backend.model.QuizAttempt;
import com.quizapp.backend.repository.QuestionRepository;
import com.quizapp.backend.repository.QuizAttemptRepository;
import com.quizapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class QuizAttemptService {

    @Autowired
    private QuizAttemptRepository attemptRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    // ── Submit quiz & calculate score ─────────────────
    public int submitQuiz(Long quizId,
                          Long userId,
                          Map<String, String> answers) {

        List<Question> questions =
                questionRepository.findByQuizId(quizId);

        System.out.println(">>> Total questions in DB for quiz "
            + quizId + ": " + questions.size());
        System.out.println(">>> Answers map from frontend: " + answers);

        int score = 0;

        for (Question q : questions) {

            String key       = String.valueOf(q.getId());
            String submitted = answers.get(key);          // full text e.g. "8 bytes"
            String correct   = q.getCorrectAnswer();      // letter e.g. "B"

            // ✅ Convert correct answer letter → actual option text
            String correctText = getOptionText(q, correct);

            System.out.println("  Q" + q.getId()
                + " | submitted='"  + submitted
                + "' | correctLetter='" + correct
                + "' | correctText='"  + correctText + "'");

            if (submitted != null
                    && submitted.trim()
                                .equalsIgnoreCase(correctText.trim())) {
                score++;
                System.out.println("    ✅ CORRECT!");
            } else {
                System.out.println("    ❌ Wrong or not answered");
            }
        }

        System.out.println(">>> FINAL SCORE: " + score
                         + "/" + questions.size());

        // ✅ Save the attempt
        QuizAttempt attempt = new QuizAttempt();
        attempt.setUserId(userId);
        attempt.setQuizId(quizId);
        attempt.setScore(score);
        attempt.setSubmittedAt(LocalDateTime.now());
        attemptRepository.save(attempt);

        return score;
    }

    // ✅ Converts "A" / "B" / "C" / "D" → actual option text from Question
    private String getOptionText(Question q, String letter) {
        if (letter == null) return "";
        switch (letter.trim().toUpperCase()) {
            case "A": return q.getOptionA() != null ? q.getOptionA() : "";
            case "B": return q.getOptionB() != null ? q.getOptionB() : "";
            case "C": return q.getOptionC() != null ? q.getOptionC() : "";
            case "D": return q.getOptionD() != null ? q.getOptionD() : "";
            default:  return letter; // already full text — compare directly
        }
    }

    // ── Get leaderboard ───────────────────────────────
    public List<LeaderboardDTO> getLeaderboard(Long quizId) {

        List<QuizAttempt> attempts =
                attemptRepository
                    .findByQuizIdOrderByScoreDesc(quizId);

        List<LeaderboardDTO> leaderboard = new ArrayList<>();
        int rank = 1;

        for (QuizAttempt a : attempts) {

            String username = userRepository
                    .findById(a.getUserId())
                    .map(u -> u.getUsername())
                    .orElse("Unknown");

            LeaderboardDTO entry = new LeaderboardDTO(
                rank++,
                username,
                a.getScore(),
                a.getSubmittedAt()
            );

            leaderboard.add(entry);
        }

        return leaderboard;
    }
}
