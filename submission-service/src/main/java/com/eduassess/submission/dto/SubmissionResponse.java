package com.eduassess.submission.dto;
import java.time.Instant;
public record SubmissionResponse(Long submissionId,Long examId,Long studentId,Instant startedAt,
                                 Instant deadline,Instant submittedAt,String status,int answerCount) {}
