package org.sbsplus.qna.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sbsplus.qna.AnswerForm;
import org.sbsplus.qna.QuestionForm;
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
        this.questionService.create(questionForm.getSubject(), questionForm.getSubject(), category);
        return "redirect:/question";
    }
}