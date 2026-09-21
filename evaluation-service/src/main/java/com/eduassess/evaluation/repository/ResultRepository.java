package com.eduassess.evaluation.repository;
import com.eduassess.evaluation.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ResultRepository extends JpaRepository<Result,Long>{
 Optional<Result> findBySubmissionId(Long submissionId);
 List<Result> findByStudentId(Long studentId);
 List<Result> findByExamId(Long examId);
}
