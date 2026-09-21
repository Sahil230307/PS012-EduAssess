package com.eduassess.submission.entity;
import jakarta.persistence.*;
@Entity @Table(name="submission_answers")
public class SubmissionAnswer {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="submission_id",nullable=false) private Submission submission;
 @Column(nullable=false) private Long questionId;
 @Column(length=1) private String selectedOption;
 public SubmissionAnswer(){}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Submission getSubmission(){return submission;} public void setSubmission(Submission v){submission=v;}
 public Long getQuestionId(){return questionId;} public void setQuestionId(Long v){questionId=v;}
 public String getSelectedOption(){return selectedOption;} public void setSelectedOption(String v){selectedOption=v;}
}
