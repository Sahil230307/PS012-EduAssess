package com.eduassess.exam.entity;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity @Table(name="exams")
public class Exam {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String title;
    @Column(length=2000) private String description;
    @Column(nullable=false) private Integer durationMinutes;
    @Column(nullable=false) private Instant startTime;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private ExamStatus status;
    @Column(nullable=false) private Long createdBy;

    @OneToMany(mappedBy="exam", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Question> questions = new ArrayList<>();

    public Exam(){}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public Integer getDurationMinutes(){return durationMinutes;} public void setDurationMinutes(Integer v){durationMinutes=v;}
    public Instant getStartTime(){return startTime;} public void setStartTime(Instant v){startTime=v;}
    public ExamStatus getStatus(){return status;} public void setStatus(ExamStatus v){status=v;}
    public Long getCreatedBy(){return createdBy;} public void setCreatedBy(Long v){createdBy=v;}
    public List<Question> getQuestions(){return questions;}
}
