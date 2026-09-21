package com.eduassess.exam.dto;
import com.eduassess.exam.entity.ExamStatus;
import java.time.Instant;
public record ExamRuntimeResponse(Long id, Instant startTime, Integer durationMinutes, ExamStatus status) {}
