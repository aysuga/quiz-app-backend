package com.quizapp.backend.model;  // ✅ Must match folder!

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_attempts")
public class QuizAttempt {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;   // ✅ Must be Long


    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "quiz_id", nullable = false)
    private Long quizId;

    @Column(nullable = false)
    private int score;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @PrePersist
    public void prePersist() {
        this.submittedAt = LocalDateTime.now();
    }

    // ─── GETTERS & SETTERS ───────────────────────────
    public Long getId()                          { return id; }
    public void setId(Long id)                   { this.id = id; }

    public Long getUserId()                      { return userId; }
    public void setUserId(Long u)                { this.userId = u; }

    public Long getQuizId()                      { return quizId; }
    public void setQuizId(Long quizId)                { this.quizId = quizId; }

    public int getScore()                    { return score; }
    public void setScore(int s)              { this.score = s; }

    public LocalDateTime getSubmittedAt()        { return submittedAt; }
    public void setSubmittedAt(LocalDateTime t)  { this.submittedAt = t; }
}
