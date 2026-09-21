package com.eduassess.exam.dto;
import jakarta.validation.constraints.*;
public record CreateQuestionRequest(@NotBlank String questionText, @NotBlank String optionA,
 @NotBlank String optionB, @NotBlank String optionC, @NotBlank String optionD,
 @NotBlank @Pattern(regexp="[ABCDabcd]") String correctOption, @NotNull @Min(1) Integer marks) {}
