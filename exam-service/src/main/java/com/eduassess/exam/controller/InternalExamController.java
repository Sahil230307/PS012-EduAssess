package com.eduassess.exam.controller;
import com.eduassess.exam.dto.*;
import com.eduassess.exam.service.ExamService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/internal/exams")
public class InternalExamController {
    private final ExamService service;
    public InternalExamController(ExamService service){this.service=service;}
    @GetMapping("/{id}/runtime") @PreAuthorize("hasRole('SERVICE')")
    public ExamRuntimeResponse runtime(@PathVariable Long id){return service.runtime(id);}
    @GetMapping("/{id}/answer-key") @PreAuthorize("hasRole('SERVICE')")
    public List<AnswerKeyItem> answerKey(@PathVariable Long id){return service.answerKey(id);}
}
