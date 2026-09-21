package com.eduassess.exam.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
public record CreateExamRequest(@NotBlank String title, String description,
                                @NotNull @Min(1) Integer durationMinutes, Instant startTime) {}
