package com.eduassess.submission.dto;
import jakarta.validation.Valid;
import java.util.List;
public record SubmitRequest(List<@Valid AnswerRequest> answers) {}
