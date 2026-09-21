package com.eduassess.submission.client;
import com.eduassess.submission.dto.EvaluationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(name="EVALUATION-SERVICE")
public interface EvaluationClient {
 @PostMapping("/evaluations/internal/evaluate")
 Map<String,Object> evaluate(@RequestBody EvaluationRequest request);
}
