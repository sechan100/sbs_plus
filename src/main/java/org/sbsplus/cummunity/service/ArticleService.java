package org.sbsplus.cummunity.service;

import org.sbsplus.cummunity.dto.ArticleDto;
import org.sbsplus.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;

public interface ArticleService {
    
    Page<ArticleDto> findByCategory(int page, Category category);
    
    ArticleDto findById(Integer articleId);
    
    void increaseHit(Integer articleId);
    
    void save(ArticleDto articleDto);
    
    boolean checkArticleOwnership(Integer articleId);
    
    void delete(Integer articleId) throws AccessDeniedException;
    
    boolean hasUserLiked(Integer articleId);
    
    void likeArticle(Integer articleId);
    
    void unlikeArticle(Integer articleId);
}
