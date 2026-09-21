package com.eduassess.exam.dto;
import com.eduassess.exam.entity.ExamStatus;
import java.time.Instant;
public record ExamResponse(Long id,String title,String description,Integer durationMinutes,
                           Instant startTime,ExamStatus status,Long createdBy) {}
