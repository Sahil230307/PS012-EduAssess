package com.eduassess.submission.controller;
import com.eduassess.submission.dto.*;
import com.eduassess.submission.service.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/submissions")
public class SubmissionController {
 private final SubmissionService service;
 public SubmissionController(SubmissionService service){this.service=service;}

 @PostMapping("/exams/{examId}/start") @PreAuthorize("hasRole('STUDENT')")
 public StartResponse start(@PathVariable Long examId,Authentication a){return service.start(examId,userId(a));}

 @PostMapping("/{submissionId}/submit") @PreAuthorize("hasRole('STUDENT')")
 public Map<String,Object> submit(@PathVariable Long submissionId,@Valid @RequestBody SubmitRequest r,Authentication a){
   return service.submit(submissionId,userId(a),r);
 }

 @GetMapping("/{submissionId}") @PreAuthorize("hasRole('STUDENT')")
 public SubmissionResponse get(@PathVariable Long submissionId,Authentication a){return service.get(submissionId,userId(a));}

 @GetMapping("/mine") @PreAuthorize("hasRole('STUDENT')")
 public List<SubmissionResponse> mine(Authentication a){return service.mySubmissions(userId(a));}

 @GetMapping("/exam/{examId}") @PreAuthorize("hasRole('ADMIN')")
 public List<SubmissionResponse> exam(@PathVariable Long examId){return service.examSubmissions(examId);}

 private Long userId(Authentication a){try{return Long.valueOf(String.valueOf(a.getDetails()));}catch(Exception e){throw new SecurityException("Invalid user identity");}}
}
