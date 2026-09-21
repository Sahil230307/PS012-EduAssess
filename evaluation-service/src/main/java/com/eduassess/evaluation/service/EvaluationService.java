package com.eduassess.evaluation.service;
import com.eduassess.evaluation.dto.*;
import com.eduassess.evaluation.entity.Result;
import com.eduassess.evaluation.repository.ResultRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
public class EvaluationService {
 private final ResultRepository repository;
 public EvaluationService(ResultRepository repository){this.repository=repository;}

 public ResultResponse evaluate(EvaluationRequest request){
   var existing=repository.findBySubmissionId(request.submissionId());
   if(existing.isPresent()) return toResponse(existing.get());
   int score=0,total=0;
   for(var a:request.answers()){
     int marks=a.marks()==null?0:a.marks();
     total+=marks;
     if(a.selectedOption()!=null && a.correctOption()!=null &&
        a.selectedOption().equalsIgnoreCase(a.correctOption())) score+=marks;
   }
   Result r=new Result();
   r.setSubmissionId(request.submissionId());r.setExamId(request.examId());r.setStudentId(request.studentId());
   r.setScore(score);r.setTotalMarks(total);r.setPercentage(total==0?0.0:(score*100.0/total));r.setEvaluatedAt(Instant.now());
   return toResponse(repository.save(r));
 }
 public ResultResponse get(Long id){return toResponse(repository.findById(id).orElseThrow(()->new IllegalArgumentException("Result not found")));}
 public ResultResponse bySubmission(Long submissionId){return repository.findBySubmissionId(submissionId).map(this::toResponse)
   .orElseThrow(()->new IllegalArgumentException("Result not found"));}
 public List<ResultResponse> byStudent(Long studentId){return repository.findByStudentId(studentId).stream().map(this::toResponse).toList();}
 public List<ResultResponse> byExam(Long examId){return repository.findByExamId(examId).stream().map(this::toResponse).toList();}
 private ResultResponse toResponse(Result r){return new ResultResponse(r.getId(),r.getSubmissionId(),r.getExamId(),r.getStudentId(),
   r.getScore(),r.getTotalMarks(),r.getPercentage(),r.getEvaluatedAt(),"EVALUATED");}
}
