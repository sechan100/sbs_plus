package org.sbsplus.cummunity.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.sbsplus.cummunity.dto.ArticleDto;
import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.cummunity.repository.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    
    private final ArticleRepository articleRepository;
    
    @Override
    public Page<ArticleDto> findAll(int page){
        
        try {
            
            PageRequest pageRequest = PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "createAt"));
            Page<Article> articles_ = articleRepository.findAll(pageRequest);
            
            return articles_.map(article -> (new ModelMapper()).map(article, ArticleDto.class));
            
        } catch(IllegalArgumentException e){
            
            return null;
        }
    }

}
