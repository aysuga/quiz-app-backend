package com.quizapp.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;   // ✅ Must be Long


    @Column(name = "quiz_id", nullable = false)
    private Long quizId;

    @Column(name = "question_text", nullable = false,
            columnDefinition = "TEXT")
    private String questionText;

    @Column(name = "option_a")
    private String optionA;

    @Column(name = "option_b")
    private String optionB;

    @Column(name = "option_c")
    private String optionC;

    @Column(name = "option_d")
    private String optionD;

    // ✅ Matches column name in your DB after our fix
    @Column(name = "correct_answer")
    private String correctAnswer;

    // ─── GETTERS & SETTERS ───────────────────────────
    public Long getId()                          { return id; }
    public void setId(Long id)                   { this.id = id; }

    public Long getQuizId()                      { return quizId; }
    public void setQuizId(Long quizId2)           { this.quizId = quizId2; }

    public String getQuestionText()              { return questionText; }
    public void setQuestionText(String t)        { this.questionText = t; }

    public String getOptionA()                   { return optionA; }
    public void setOptionA(String o)             { this.optionA = o; }

    public String getOptionB()                   { return optionB; }
    public void setOptionB(String o)             { this.optionB = o; }

    public String getOptionC()                   { return optionC; }
    public void setOptionC(String o)             { this.optionC = o; }

    public String getOptionD()                   { return optionD; }
    public void setOptionD(String o)             { this.optionD = o; }

    public String getCorrectAnswer()             { return correctAnswer; }
    public void setCorrectAnswer(String a)       { this.correctAnswer = a; }
}
