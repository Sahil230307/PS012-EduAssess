package com.eduassess.evaluation.dto;
import java.time.Instant;
public record ResultResponse(Long id,Long submissionId,Long examId,Long studentId,Integer score,
 Integer totalMarks,Double percentage,Instant evaluatedAt,String status){}
