package com.eduassess.submission.dto;
import java.time.Instant;
public record ExamRuntimeResponse(Long id,Instant startTime,Integer durationMinutes,String status) {}
