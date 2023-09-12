package org.sbsplus.cummunity.controller;


import lombok.RequiredArgsConstructor;
import org.sbsplus.cummunity.dto.CommentDto;
import org.sbsplus.cummunity.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {
    
    private final ArticleService articleService;
    
    
    @PostMapping("/comment/write")
    public String commentWrite(@RequestParam Integer articleId, CommentDto commentDto){
        
        articleService.addComment(articleId, commentDto);
        
        return "redirect:/article/" + articleId;
    }
    
    
    
}
