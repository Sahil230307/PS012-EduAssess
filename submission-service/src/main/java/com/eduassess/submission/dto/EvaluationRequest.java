package com.eduassess.submission.dto;
import java.util.List;
public record EvaluationRequest(Long submissionId,Long examId,Long studentId,List<EvaluationAnswer> answers) {
 public record EvaluationAnswer(Long questionId,String selectedOption,String correctOption,Integer marks){}
}
