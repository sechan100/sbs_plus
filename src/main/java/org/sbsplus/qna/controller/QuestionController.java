package org.sbsplus.qna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sbsplus.qna.form.AnswerForm;
import org.sbsplus.qna.form.AnswerForm;
import org.sbsplus.qna.form.QuestionForm;
import org.sbsplus.qna.service.AnswerService;
import org.sbsplus.qna.service.QuestionService;
import org.sbsplus.qna.entity.Question;
import org.sbsplus.qna.repository.QuestionRepository;
import org.sbsplus.type.Category;
import org.sbsplus.util.Rq;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    private final AnswerService answerService;


    @GetMapping("")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "/qna/question_list";
    }
    @GetMapping(value="/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "/qna/question_detail";
    }
    @GetMapping("/create")
    public String questionCreate(Model model, QuestionForm questionForm) {
        model.addAttribute("categories", Category.getCategories());
        return "/qna/question_form";
    }
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, @RequestParam String category) {
        if(bindingResult.hasErrors()) {
            return "/qna/question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getSubject(), questionForm.getPoint(), category);
        return "redirect:/question";
    }

    @PostMapping("/{questionId}/accept/{answerId}")
    public String acceptAnswer(@PathVariable Integer questionId, @PathVariable Integer answerId) {
        // answerService를 사용하여 댓글을 채택하고 포인트를 지급하는 로직을 호출
        answerService.markAnswerAsAccepted(answerId, questionId);

        // 채택 완료 후 리다이렉트 또는 다른 응답 방식을 선택
        return "redirect:/question"; // 예시: 채택된 질문 페이지로 리다이렉트
    }
}