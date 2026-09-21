package com.eduassess.exam.entity;
import jakarta.persistence.*;

@Entity @Table(name="questions")
public class Question {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY, optional=false) @JoinColumn(name="exam_id", nullable=false) private Exam exam;
    @Column(nullable=false, length=4000) private String questionText;
    @Column(nullable=false) private String optionA;
    @Column(nullable=false) private String optionB;
    @Column(nullable=false) private String optionC;
    @Column(nullable=false) private String optionD;
    @Column(nullable=false, length=1) private String correctOption;
    @Column(nullable=false) private Integer marks;

    public Question(){}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public Exam getExam(){return exam;} public void setExam(Exam v){exam=v;}
    public String getQuestionText(){return questionText;} public void setQuestionText(String v){questionText=v;}
    public String getOptionA(){return optionA;} public void setOptionA(String v){optionA=v;}
    public String getOptionB(){return optionB;} public void setOptionB(String v){optionB=v;}
    public String getOptionC(){return optionC;} public void setOptionC(String v){optionC=v;}
    public String getOptionD(){return optionD;} public void setOptionD(String v){optionD=v;}
    public String getCorrectOption(){return correctOption;} public void setCorrectOption(String v){correctOption=v;}
    public Integer getMarks(){return marks;} public void setMarks(Integer v){marks=v;}
}
