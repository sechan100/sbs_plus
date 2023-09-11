package org.sbsplus.cummunity.controller;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sbsplus.cummunity.dto.ArticleDto;
import org.sbsplus.cummunity.service.ArticleService;
import org.sbsplus.type.Category;
import org.sbsplus.user.dto.UserDto;
import org.sbsplus.user.entity.User;
import org.sbsplus.util.Pager;
import org.sbsplus.util.Rq;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/article")
@RequiredArgsConstructor
@ControllerAdvice
public class ArticleController {
    
    private final ArticleService articleService;
    private final Rq rq;
    
    @GetMapping("")
    public String articleList(
            @RequestParam(defaultValue = "1") Integer page
            , Model model
            ){
        
        List<Category> categories = Category.getCategories();
        Page<ArticleDto> articles = articleService.findAll(page-1);
        
        if(articles == null || page > articles.getTotalPages()) {
            return rq.unexpectedRequestForWardUri("존재하지 않는 페이지입니다.");
        }
        
        Integer totalPage = articles.getTotalPages();
        
        
        
        List<Integer> pageRange = Pager.getPageRange(page, totalPage);
        
        
        model.addAttribute("categories", categories);
        model.addAttribute("articles", articles);
        model.addAttribute("pageRange", pageRange);
        
        
        
        
        return "/article/articleList";
    }
    
}
