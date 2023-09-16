package org.sbsplus.domain.cummunity.service;

import org.sbsplus.domain.cummunity.dto.ArticleDto;
import org.sbsplus.domain.cummunity.dto.CommentDto;
import org.sbsplus.general.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;

public interface ArticleService {
    
    Page<ArticleDto> findByCategory(int page, Category category);
    
    Page<ArticleDto> findBySearchMatcher(int page, Category category, String searchMatcher);
    
    ArticleDto findById(Integer articleId);
    
    void increaseHit(Integer articleId);
    
    void save(ArticleDto articleDto);
    
    boolean checkArticleOwnership(Integer articleId);
    
    void delete(Integer articleId) throws AccessDeniedException;
    
    boolean hasUserLiked(Integer articleId);
    
    void likeArticle(Integer articleId);
    
    void unlikeArticle(Integer articleId);
    
    void saveComment(Integer articleId, CommentDto commentDto);

    void deleteComment(Integer articleId, Integer commentId);

    boolean checkCommentOwnership(Integer articleId, Integer commentId);
    
    void unlikeComment(Integer articleId, Integer commentId);
    
    void likeComment(Integer articleId, Integer commentId);
    
    boolean hasUserLikedComment(Integer articleId, Integer commentId);
}
