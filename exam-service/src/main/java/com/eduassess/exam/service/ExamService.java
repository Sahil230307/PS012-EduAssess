package com.eduassess.exam.service;
import com.eduassess.exam.dto.*;
import com.eduassess.exam.entity.*;
import com.eduassess.exam.repository.*;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
public class ExamService {
    private final ExamRepository exams;
    private final QuestionRepository questions;
    public ExamService(ExamRepository exams, QuestionRepository questions){this.exams=exams;this.questions=questions;}

    public ExamResponse create(CreateExamRequest r, Long userId) {
        Exam e=new Exam();
        e.setTitle(r.title()); e.setDescription(r.description());
        e.setDurationMinutes(r.durationMinutes()); e.setStartTime(r.startTime()==null?Instant.now():r.startTime());
        e.setStatus(ExamStatus.DRAFT); e.setCreatedBy(userId);
        e=exams.save(e);
        return toResponse(e);
    }
    public ExamResponse publish(Long id) {
        Exam e=find(id); e.setStatus(ExamStatus.PUBLISHED); return toResponse(exams.save(e));
    }
    public ExamResponse close(Long id) {
        Exam e=find(id); e.setStatus(ExamStatus.CLOSED); return toResponse(exams.save(e));
    }
    public List<ExamResponse> listPublished(){return exams.findAll().stream().filter(e->e.getStatus()==ExamStatus.PUBLISHED).map(this::toResponse).toList();}
    public List<ExamResponse> listAll(){return exams.findAll().stream().map(this::toResponse).toList();}
    public ExamResponse get(Long id){return toResponse(find(id));}
    public List<QuestionResponse> publicQuestions(Long examId){
        find(examId);
        return questions.findByExamId(examId).stream().map(q->new QuestionResponse(q.getId(),q.getQuestionText(),
                q.getOptionA(),q.getOptionB(),q.getOptionC(),q.getOptionD(),q.getMarks())).toList();
    }
    public Question createQuestion(Long examId, CreateQuestionRequest r){
        Exam e=find(examId);
        Question q=new Question(); q.setExam(e); q.setQuestionText(r.questionText());
        q.setOptionA(r.optionA());q.setOptionB(r.optionB());q.setOptionC(r.optionC());q.setOptionD(r.optionD());
        q.setCorrectOption(r.correctOption().toUpperCase());q.setMarks(r.marks());
        return questions.save(q);
    }
    public ExamRuntimeResponse runtime(Long examId){Exam e=find(examId);return new ExamRuntimeResponse(e.getId(),e.getStartTime(),e.getDurationMinutes(),e.getStatus());}
    public List<AnswerKeyItem> answerKey(Long examId){find(examId);return questions.findByExamId(examId).stream()
            .map(q->new AnswerKeyItem(q.getId(),q.getCorrectOption(),q.getMarks())).toList();}
    private Exam find(Long id){return exams.findById(id).orElseThrow(()->new IllegalArgumentException("Exam not found: "+id));}
    private ExamResponse toResponse(Exam e){return new ExamResponse(e.getId(),e.getTitle(),e.getDescription(),e.getDurationMinutes(),e.getStartTime(),e.getStatus(),e.getCreatedBy());}
}
