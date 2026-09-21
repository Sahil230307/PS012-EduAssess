package com.eduassess.evaluation.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduassess.evaluation.dto.EvaluationRequest;
import com.eduassess.evaluation.dto.ResultResponse;
import com.eduassess.evaluation.service.EvaluationService;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

    private final EvaluationService service;

    public EvaluationController(EvaluationService service) {
        this.service = service;
    }

    @PostMapping("/internal/evaluate")
    @PreAuthorize("hasRole('SERVICE')")
    public ResultResponse evaluate(@RequestBody EvaluationRequest request) {
        return service.evaluate(request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResultResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/submission/{submissionId}")
    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    public ResultResponse bySubmission(
            @PathVariable Long submissionId,
            Authentication authentication) {

        ResultResponse result = service.bySubmission(submissionId);

        Long authenticatedUserId = userId(authentication);

        if (authentication.getAuthorities().stream()
                .anyMatch(x -> x.getAuthority().equals("ROLE_ADMIN"))) {
            return result;
        }

        if (!Objects.equals(result.studentId(), authenticatedUserId)) {
            throw new SecurityException("Access denied");
        }

        return result;
    }

    @GetMapping("/mine")
    @PreAuthorize("hasRole('STUDENT')")
    public List<ResultResponse> mine(Authentication authentication) {
        return service.byStudent(userId(authentication));
    }

    @GetMapping("/exam/{examId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ResultResponse> exam(@PathVariable Long examId) {
        return service.byExam(examId);
    }

    private Long userId(Authentication authentication) {
        try {
            return Long.valueOf(
                    String.valueOf(authentication.getDetails())
            );
        } catch (Exception e) {
            throw new SecurityException("Invalid user identity");
        }
    }
}