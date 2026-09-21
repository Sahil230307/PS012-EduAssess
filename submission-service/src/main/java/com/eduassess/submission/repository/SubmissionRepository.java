package com.eduassess.submission.repository;
import com.eduassess.submission.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface SubmissionRepository extends JpaRepository<Submission,Long>{
 Optional<Submission> findByExamIdAndStudentId(Long examId,Long studentId);
 List<Submission> findByStudentId(Long studentId);
 List<Submission> findByExamId(Long examId);
}
