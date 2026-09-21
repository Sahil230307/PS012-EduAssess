package com.eduassess.exam.repository;
import com.eduassess.exam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findByExamId(Long examId);
}
