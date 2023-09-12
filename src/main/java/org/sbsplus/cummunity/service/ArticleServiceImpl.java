package org.sbsplus.cummunity.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sbsplus.admin.AdminService;
import org.sbsplus.cummunity.dto.ArticleDto;
import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.cummunity.repository.ArticleRepository;
import org.sbsplus.type.Category;
import org.sbsplus.user.entity.User;
import org.sbsplus.user.service.UserService;
import org.sbsplus.util.Rq;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    
    private final ArticleRepository articleRepository;
    private final AdminService adminService;
    private final UserService userService;
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
    @Transactional
    public void save(ArticleDto articleDto) {
        
        // 새로운 글 작성
        if(articleDto.getId() == null){
            
            Article article = (new ModelMapper()).map(articleDto, Article.class);
            
            if(article.getUser() == null){
                article.setUser(rq.getUser());
            }
            
            articleRepository.save(article);
            
        // 기존 글 수정
        } else {
            Article article = articleRepository.findById(articleDto.getId()).orElseThrow();
                article.setCategory(articleDto.getCategory());
                article.setTitle(articleDto.getTitle());
                article.setContent(articleDto.getContent());
        }
        
    }
    
    
    @Override
    @Transactional
    public boolean validatePermissionForArticle(Integer articleId){
        
        Article article = articleRepository.findById(articleId).orElseThrow();
        
        
        // admin도 수정은 못함
        if(rq.getUser() == null){
            return false;
        }
        
        
        
        User user_ = rq.getUser();
        User user = userService.findByUsername(user_.getUsername());
        
        return article.getUser() == user;
    }
}












