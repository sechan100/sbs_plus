package org.sbsplus.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.cummunity.entity.Article;
import org.sbsplus.domain.cummunity.service.ArticleService;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.domain.qna.service.QuestionService;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.util.Rq;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MypageController {
    
    private final Rq rq;
    private final ArticleService articleService;
    private final QuestionService questionService;
    
    
    @GetMapping("/@{username}")
    public String mypage(@PathVariable String username){
        
        // 현재 로그인 중인 유저의 접근이 아닌 경우
        if(!rq.getUser().getUsername().equals(username)){
            throw new AccessDeniedException("해당 계정에 대한 접근 권한이 없습니다.");
        }
        
        return "/user/mypage";
    }
    
    @GetMapping("/@{username}/article")
    public String myArticle(@PathVariable String username, Model model){
        User user = rq.getUser();
        List<Article> articles = articleService.findByUser(user);
        
        model.addAttribute("articles", articles);
        
        
        
        return "/user/myArticle";
    }

    @GetMapping("/@{username}/question")
    public String myQuestion(@PathVariable String username, Model model){
        User user = rq.getUser();
        List<Question> questions = questionService.findByUser(user);

        model.addAttribute("questions", questions);


        return "/user/myQuestion";
    }
}
