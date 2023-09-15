package org.sbsplus.domain.qna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.qna.service.AnswerService;
import org.sbsplus.domain.qna.form.AnswerForm;
import org.sbsplus.domain.qna.form.QuestionForm;
import org.sbsplus.domain.qna.service.QuestionService;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.general.type.Category;
import org.sbsplus.util.Rq;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

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
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), questionForm.getPoint(), category);
        return "redirect:/question";
    }

    @PostMapping("/{questionId}/accept/{answerId}")
    public String acceptAnswer(@PathVariable Integer questionId, @PathVariable Integer answerId) {
        // answerService를 사용하여 댓글을 채택하고 포인트를 지급하는 로직을 호출
        answerService.markAnswerAsAccepted(answerId, questionId);

        // 채택 완료 후 리다이렉트 또는 다른 응답 방식을 선택
        return "redirect:/question"; // 예시: 채택된 질문 페이지로 리다이렉트
    }
    @GetMapping("/modify/{id}")
    public String questionModify(Model model, QuestionForm questionForm, @PathVariable("id") Integer id, Rq rq) {
        Question question = this.questionService.getQuestion(id);

        if(!question.getUser().getUsername().equals(rq.getUser().getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        model.addAttribute("categories", Category.getCategories());
        model.addAttribute("question", question);

        questionForm.setPoint(question.getPoint());
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "/qna/question_modify";
    }

    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Rq rq, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "/qna/question_modify";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getUser().getUsername().equals(rq.getUser().getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent(), questionForm.getCategory());
        return String.format("redirect:/question/detail/%s", id);
    }

    @GetMapping("/delete/{id}")
    public String questionDelete(Rq rq, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getUser().getUsername().equals(rq.getUser().getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }
}