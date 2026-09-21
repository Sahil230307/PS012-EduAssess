package com.eduassess.evaluation.entity;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="results",uniqueConstraints=@UniqueConstraint(columnNames="submission_id"))
public class Result {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="submission_id",nullable=false) private Long submissionId;
 @Column(nullable=false) private Long examId;
 @Column(nullable=false) private Long studentId;
 @Column(nullable=false) private Integer score;
 @Column(nullable=false) private Integer totalMarks;
 @Column(nullable=false) private Double percentage;
 @Column(nullable=false) private Instant evaluatedAt;
 public Result(){}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Long getSubmissionId(){return submissionId;} public void setSubmissionId(Long v){submissionId=v;}
 public Long getExamId(){return examId;} public void setExamId(Long v){examId=v;}
 public Long getStudentId(){return studentId;} public void setStudentId(Long v){studentId=v;}
 public Integer getScore(){return score;} public void setScore(Integer v){score=v;}
 public Integer getTotalMarks(){return totalMarks;} public void setTotalMarks(Integer v){totalMarks=v;}
 public Double getPercentage(){return percentage;} public void setPercentage(Double v){percentage=v;}
 public Instant getEvaluatedAt(){return evaluatedAt;} public void setEvaluatedAt(Instant v){evaluatedAt=v;}
}
