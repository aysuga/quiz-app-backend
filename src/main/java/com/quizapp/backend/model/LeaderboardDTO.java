package com.quizapp.backend.model;

import java.time.LocalDateTime;

public class LeaderboardDTO {

    private int rank;
    private String username;
    private int score;
    private LocalDateTime submittedAt;

    // ── Constructor ───────────────────────────────────
    public LeaderboardDTO(int rank,
                          String username,
                          int score,
                          LocalDateTime submittedAt) {
        this.rank        = rank;
        this.username    = username;
        this.score       = score;
        this.submittedAt = submittedAt;
    }

    // ── Getters ───────────────────────────────────────
    public int getRank()                   { return rank; }
    public String getUsername()            { return username; }
    public int getScore()                  { return score; }
    public LocalDateTime getSubmittedAt()  { return submittedAt; }

    // ── Setters ───────────────────────────────────────
    public void setRank(int rank)                      { this.rank = rank; }
    public void setUsername(String username)            { this.username = username; }
    public void setScore(int score)                    { this.score = score; }
    public void setSubmittedAt(LocalDateTime t)        { this.submittedAt = t; }
}
