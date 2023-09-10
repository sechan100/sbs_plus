package org.sbsplus.cummunity.service;

import org.sbsplus.cummunity.dto.ArticleDto;
import org.springframework.data.domain.Page;

public interface ArticleService {
    
    Page<ArticleDto> findAll(int page);
}
