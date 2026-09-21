package com.eduassess.submission.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
public record AnswerRequest(@NotNull Long questionId,@Pattern(regexp="[ABCDabcd]") String selectedOption) {}
