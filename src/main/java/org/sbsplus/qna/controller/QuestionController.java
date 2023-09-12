package org.sbsplus.qna.controller;

import lombok.RequiredArgsConstructor;
import org.sbsplus.qna.service.QuestionService;
import org.sbsplus.qna.entity.Question;
import org.sbsplus.qna.repository.QuestionRepository;
import org.sbsplus.type.Category;
import org.sbsplus.util.Rq;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


    @GetMapping("")
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "/qna/question_list";
    }
    @GetMapping(value="/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "/qna/question_detail";
    }
    @GetMapping("/create")
    public String questionCreate(Model model) {
        model.addAttribute("categories", Category.getCategories());
        return "/qna/question_form";
    }
    @PostMapping("/create")
    public String questionCreate(@RequestParam String subject, @RequestParam String content, @RequestParam String category) {
        this.questionService.create(subject, content, category);
        return "redirect:/question";
    }
}