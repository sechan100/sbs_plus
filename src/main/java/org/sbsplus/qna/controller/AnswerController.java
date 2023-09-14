package org.sbsplus.qna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sbsplus.qna.form.AnswerForm;
import org.sbsplus.qna.entity.Answer;
import org.sbsplus.qna.entity.Question;
import org.sbsplus.qna.service.AnswerService;
import org.sbsplus.qna.service.QuestionService;
import org.sbsplus.util.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}