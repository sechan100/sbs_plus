package org.sbsplus.cummunity.service;

import org.sbsplus.cummunity.entity.Article;
import org.springframework.data.domain.Page;

public interface ArticleService {
    
    Page<Article> findAll(int page);
}
