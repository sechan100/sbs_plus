package org.sbsplus.cummunity.controller;


import lombok.RequiredArgsConstructor;
import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.cummunity.repository.ArticleRepository;
import org.sbsplus.type.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleListController {
    
    private final ArticleRepository articleRepository;
    
    @GetMapping("")
    public String articleList(Model model){
        
        List<Article> articles = articleRepository.findAll();
        List<Subject> Subjects = Subject.getSubjects();
        
        
        return "/article/articleList";
    }
    
}
