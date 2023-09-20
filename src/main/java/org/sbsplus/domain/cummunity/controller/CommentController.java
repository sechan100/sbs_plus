package org.sbsplus.domain.cummunity.controller;


import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.cummunity.dto.CommentDto;
import org.sbsplus.domain.cummunity.service.ArticleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class CommentController {
    
    private final ArticleService articleService;
    
    // 댓글 작성 & 수정
    @PostMapping("/comment/write")
    @ResponseBody
    public String commentWrite
            ( @RequestParam Integer articleId
            , @RequestParam(required = false) Integer commentId
            , CommentDto commentDto){
        
        
        // 아이디 있음 -> 수정
        // 아이디 없음 -> 등록
        commentDto.setId(commentId);
        
        articleService.saveComment(articleId, commentDto);
        
        
        
        return "댓글 작성 성공이야~~~";
    }
    
    // 댓글 삭제
    @GetMapping("/comment/delete")
    public String deleteComment(@RequestParam Integer articleId, @RequestParam Integer commentId){
        
        articleService.deleteComment(articleId, commentId);
        
        return "redirect:/article/" + articleId;
    }
  
    
    // 댓글 좋아요
    @GetMapping("/comment/like")
    public String commentLike(@RequestParam Integer articleId, @RequestParam Integer commentId){
      
        // 기존 추천 여부 확인
        if(articleService.hasUserLikedComment(articleId, commentId)){
            
            // 기존 추천 여부가 있다면 추천 취소
            articleService.unlikeComment(articleId, commentId);
            
        } else {
            
            // 추천한 적이 없다면 추천 생성
            articleService.likeComment(articleId, commentId);
        }
        
        
        
        return "redirect:/article/" + articleId;
    }
}



















