package com.eduassess.submission.service;

import com.eduassess.submission.client.*;
import com.eduassess.submission.dto.*;
import com.eduassess.submission.entity.*;
import com.eduassess.submission.repository.SubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SubmissionService {
 private final SubmissionRepository repository;
 private final ExamClient examClient;
 private final EvaluationClient evaluationClient;

 public SubmissionService(SubmissionRepository repository,ExamClient examClient,EvaluationClient evaluationClient){
   this.repository=repository;this.examClient=examClient;this.evaluationClient=evaluationClient;
 }

 @Transactional
 public StartResponse start(Long examId,Long studentId){
   var existing=repository.findByExamIdAndStudentId(examId,studentId);
   if(existing.isPresent()){
     var s=existing.get();
     if(s.getStatus()==SubmissionStatus.IN_PROGRESS)
       return new StartResponse(s.getId(),s.getExamId(),s.getStartedAt(),s.getDeadline(),s.getStatus().name());
     throw new IllegalStateException("A submission already exists for this exam");
   }
   ExamRuntimeResponse exam=examClient.runtime(examId);
   if(!"PUBLISHED".equals(exam.status())) throw new IllegalStateException("Exam is not published");
   Instant now=Instant.now();
   Instant start=exam.startTime()==null?now:exam.startTime();
   if(now.isBefore(start)) throw new IllegalStateException("Exam has not started yet");
   Instant deadline=start.plusSeconds(exam.durationMinutes()*60L);
   if(now.isAfter(deadline)) throw new IllegalStateException("Exam time has already expired");

   Submission s=new Submission();
   s.setExamId(examId);s.setStudentId(studentId);s.setStartedAt(now);s.setDeadline(deadline);s.setStatus(SubmissionStatus.IN_PROGRESS);
   s=repository.save(s);
   return new StartResponse(s.getId(),examId,now,deadline,s.getStatus().name());
 }

 @Transactional
 public Map<String,Object> submit(Long submissionId,Long studentId,SubmitRequest request){
   Submission s=repository.findById(submissionId).orElseThrow(()->new IllegalArgumentException("Submission not found"));
   if(!Objects.equals(s.getStudentId(),studentId)) throw new SecurityException("Submission does not belong to this student");
   if(s.getStatus()!=SubmissionStatus.IN_PROGRESS) throw new IllegalStateException("Submission is not in progress");
   if(Instant.now().isAfter(s.getDeadline())){
     s.setStatus(SubmissionStatus.EXPIRED);repository.save(s);
     throw new IllegalStateException("Submission deadline has expired");
   }

   List<AnswerKeyItem> key=examClient.answerKey(s.getExamId());
   Map<Long,String> selected = new HashMap<>();
   if (request.answers() != null) {
     for (AnswerRequest a : request.answers()) {
       selected.put(a.questionId(), a.selectedOption()==null ? null : a.selectedOption().toUpperCase());
     }
   }

   s.getAnswers().clear();
   List<EvaluationRequest.EvaluationAnswer> evaluationAnswers=new ArrayList<>();

   for(AnswerKeyItem k:key){
     String choice=selected.get(k.questionId());
     SubmissionAnswer entity=new SubmissionAnswer();
     entity.setSubmission(s);entity.setQuestionId(k.questionId());entity.setSelectedOption(choice);
     s.getAnswers().add(entity);
     evaluationAnswers.add(new EvaluationRequest.EvaluationAnswer(k.questionId(),choice,k.correctOption(),k.marks()));
   }
   s.setSubmittedAt(Instant.now());s.setStatus(SubmissionStatus.SUBMITTED);
   repository.save(s);

   Map<String,Object> result=new LinkedHashMap<>();
   var evaluated=evaluationClient.evaluate(new EvaluationRequest(s.getId(),s.getExamId(),s.getStudentId(),evaluationAnswers));
   result.putAll(evaluated);
   s.setStatus(SubmissionStatus.EVALUATED);
   repository.save(s);
   result.put("submissionId",s.getId());
   result.put("status",s.getStatus().name());
   return result;
 }

 public SubmissionResponse get(Long id,Long studentId){
   Submission s=repository.findById(id).orElseThrow(()->new IllegalArgumentException("Submission not found"));
   if(!Objects.equals(s.getStudentId(),studentId)) throw new SecurityException("Access denied");
   return toResponse(s);
 }
 public List<SubmissionResponse> mySubmissions(Long studentId){return repository.findByStudentId(studentId).stream().map(this::toResponse).toList();}
 public List<SubmissionResponse> examSubmissions(Long examId){return repository.findByExamId(examId).stream().map(this::toResponse).toList();}
 private SubmissionResponse toResponse(Submission s){return new SubmissionResponse(s.getId(),s.getExamId(),s.getStudentId(),
   s.getStartedAt(),s.getDeadline(),s.getSubmittedAt(),s.getStatus().name(),s.getAnswers().size());}
}
