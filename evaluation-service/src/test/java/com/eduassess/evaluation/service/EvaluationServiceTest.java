package com.eduassess.evaluation.service;

import com.eduassess.evaluation.dto.EvaluationRequest;
import com.eduassess.evaluation.entity.Result;
import com.eduassess.evaluation.repository.ResultRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EvaluationServiceTest {

    @Mock
    ResultRepository repository;

    @Test
    void calculatesScoreAndPercentage() {

        when(repository.findBySubmissionId(10L))
                .thenReturn(Optional.empty());

        when(repository.save(any(Result.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        EvaluationService service = new EvaluationService(repository);

        EvaluationRequest request = new EvaluationRequest(
                10L,
                20L,
                30L,
                List.of(
                        new EvaluationRequest.EvaluationAnswer(
                                1L, "A", "A", 2
                        ),
                        new EvaluationRequest.EvaluationAnswer(
                                2L, "C", "B", 3
                        ),
                        new EvaluationRequest.EvaluationAnswer(
                                3L, null, "D", 5
                        )
                )
        );

        var result = service.evaluate(request);

        assertEquals(2, result.score());
        assertEquals(10, result.totalMarks());
        assertEquals(20.0, result.percentage());

        verify(repository).save(any(Result.class));
    }
}