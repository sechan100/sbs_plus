package org.sbsplus.domain.qna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.qna.entity.Answer;
import org.sbsplus.domain.qna.service.AnswerService;
import org.sbsplus.domain.qna.form.AnswerForm;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.domain.qna.service.QuestionService;
import org.sbsplus.util.Rq;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id
            , @Valid AnswerForm answerForm, BindingResult bindingResult) {
        Question question = this.questionService.getQuestion(id);
        if(bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "/qna/question_detail";
        }
        this.answerService.create(question, answerForm.getContent());
        return String.format("redirect:/question/detail/%s",id);
    }
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Rq rq, @RequestParam Integer questionId) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getUser().getUsername().equals(rq.getUser().getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        answerForm.setQuestionId(questionId);
        return "/qna/answer_form";
    }

    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Rq rq) {
        if (bindingResult.hasErrors()) {
            return "/qna/answer_form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getUser().getUsername().equals(rq.getUser().getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerForm.getContent());
        return "redirect:/question/detail/" + answerForm.getQuestionId();
    }
}