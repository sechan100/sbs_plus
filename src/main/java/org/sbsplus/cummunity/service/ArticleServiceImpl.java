package org.sbsplus.cummunity.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sbsplus.cummunity.dto.ArticleDto;
import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.cummunity.repository.ArticleRepository;
import org.sbsplus.type.Category;
import org.sbsplus.util.Rq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    
    private final ArticleRepository articleRepository;
    private final Rq rq;
    
    @Override
    public Page<ArticleDto> findByCategory(int page, Category category){
        
        try {
            Page<Article> articles_ = null;
            
            Pageable pageRequest = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "createAt"));
            
            if(category == Category.ALL) {
                articles_ = articleRepository.findAll(pageRequest);
            } else {
                articles_ = articleRepository.findByCategory(pageRequest, category);
            }
            // N + 1 최적화 필요.
            return articles_.map(article -> (new ModelMapper()).map(article, ArticleDto.class));
            
        } catch(IllegalArgumentException e){
            
            return null;
        }
    }
    
    @Override
    public ArticleDto findById(Integer articleId) {
        
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        
        return (new ModelMapper()).map(article, ArticleDto.class);
    }
    
    @Override
    @Transactional
    public void increaseHit(Integer articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new EntityNotFoundException("게시글이 존재하지 않습니다."));
        article.increaseHit();
    }
    
    @Override
    public void save(ArticleDto articleDto) {
        
        Article article = (new ModelMapper()).map(articleDto, Article.class);
        if(article.getUser() == null){
            article.setUser(rq.getUser());
        }
        
        articleRepository.save(article);
    }
    
    
}
