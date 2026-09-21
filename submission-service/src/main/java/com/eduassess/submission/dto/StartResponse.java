package com.eduassess.submission.dto;
import java.time.Instant;
public record StartResponse(Long submissionId,Long examId,Instant startedAt,Instant deadline,String status) {}
