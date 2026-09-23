package com.eduassess.exam.controller;
import com.eduassess.exam.dto.*;
import com.eduassess.exam.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/exams")
public class ExamController {
    private final ExamService service;
    public ExamController(ExamService service){this.service=service;}

    @GetMapping public List<ExamResponse> published(){
    	
    	return service.listPublished();
    	}
    
    @GetMapping("/all") @PreAuthorize("hasRole('ADMIN')") public List<ExamResponse> all(){
    	return service.listAll();
    	}
    @GetMapping("/{id}") public ExamResponse get(@PathVariable Long id){return service.get(id);}
    @GetMapping("/{id}/questions") public List<QuestionResponse> questions(@PathVariable Long id){return service.publicQuestions(id);}

    @PostMapping @PreAuthorize("hasRole('ADMIN')")
    public ExamResponse create(@Valid @RequestBody CreateExamRequest r, Authentication a){
        return service.create(r, userId(a));
    }
    @PostMapping("/{id}/publish") @PreAuthorize("hasRole('ADMIN')")
    public ExamResponse publish(@PathVariable Long id){return service.publish(id);}
    @PostMapping("/{id}/close") @PreAuthorize("hasRole('ADMIN')")
    public ExamResponse close(@PathVariable Long id){return service.close(id);}

    @PostMapping("/{id}/questions") @PreAuthorize("hasRole('ADMIN')")
    public QuestionResponse addQuestion(@PathVariable Long id,@Valid @RequestBody CreateQuestionRequest r){
        var q=service.createQuestion(id,r);
        return new QuestionResponse(q.getId(),q.getQuestionText(),q.getOptionA(),q.getOptionB(),q.getOptionC(),q.getOptionD(),q.getMarks());
    }
    private Long userId(Authentication a){try{return Long.valueOf(String.valueOf(a.getDetails()));}catch(Exception e){return 0L;}}
}
