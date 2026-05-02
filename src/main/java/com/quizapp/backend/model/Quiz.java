package com.quizapp.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column
    private Integer duration;

    // ✅ Just a plain @Column — NOT a relationship!
    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ── Getters & Setters ──────────────────────────────
    public Long getId()                       { return id; }
    public void setId(Long id)                { this.id = id; }

    public String getTitle()                  { return title; }
    public void setTitle(String title)        { this.title = title; }

    public String getDescription()            { return description; }
    public void setDescription(String desc)   { this.description = desc; }

    public Integer getDuration()              { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public Long getCreatedBy()                { return createdBy; }
    public void setCreatedBy(Long createdBy)  { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt()       { return createdAt; }
    public void setCreatedAt(LocalDateTime t) { this.createdAt = t; }
}
