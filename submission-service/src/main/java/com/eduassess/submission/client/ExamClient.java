package com.eduassess.submission.client;
import com.eduassess.submission.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name="EXAM-SERVICE")
public interface ExamClient {
 @GetMapping("/internal/exams/{id}/runtime")
 ExamRuntimeResponse runtime(@PathVariable("id") Long id);
 @GetMapping("/internal/exams/{id}/answer-key")
 List<AnswerKeyItem> answerKey(@PathVariable("id") Long id);
}
