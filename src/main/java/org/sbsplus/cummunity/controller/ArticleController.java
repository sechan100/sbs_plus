package org.sbsplus.cummunity.controller;


import lombok.RequiredArgsConstructor;
import org.sbsplus.cummunity.dto.ArticleDto;
import org.sbsplus.cummunity.service.ArticleService;
import org.sbsplus.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    
    private final ArticleService articleService;
    
    
    @GetMapping("")
    public String articleList(
            @RequestParam(defaultValue = "1") Integer page
            , Model model
            ){
        
        List<Category> categories = Category.getCategories();
        Page<ArticleDto> articles = articleService.findAll(page-1);
        
        
        model.addAttribute("categories", categories);
        model.addAttribute("articles", articles);
        
        
        return "/article/articleList";
    }
    
}
