package com.eduassess.submission.entity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="submissions", uniqueConstraints=@UniqueConstraint(columnNames={"exam_id","student_id"}))
public class Submission {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="exam_id",nullable=false) private Long examId;
 @Column(name="student_id",nullable=false) private Long studentId;
 @Column(nullable=false) private Instant startedAt;
 @Column(nullable=false) private Instant deadline;
 private Instant submittedAt;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private SubmissionStatus status;
 @OneToMany(mappedBy="submission",cascade=CascadeType.ALL,orphanRemoval=true)
 private List<SubmissionAnswer> answers=new ArrayList<>();
 public Submission(){}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Long getExamId(){return examId;} public void setExamId(Long v){examId=v;}
 public Long getStudentId(){return studentId;} public void setStudentId(Long v){studentId=v;}
 public Instant getStartedAt(){return startedAt;} public void setStartedAt(Instant v){startedAt=v;}
 public Instant getDeadline(){return deadline;} public void setDeadline(Instant v){deadline=v;}
 public Instant getSubmittedAt(){return submittedAt;} public void setSubmittedAt(Instant v){submittedAt=v;}
 public SubmissionStatus getStatus(){return status;} public void setStatus(SubmissionStatus v){status=v;}
 public List<SubmissionAnswer> getAnswers(){return answers;}
}
