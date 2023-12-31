package org.sbsplus.domain.cummunity.repository;

import org.sbsplus.domain.cummunity.dto.ArticleDto;
import org.sbsplus.domain.cummunity.entity.Article;
import org.sbsplus.domain.notice.entity.Notice;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.general.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Page<Article> findByCategory(Pageable pageRequest, Category category);

    Page<Article> findByContentContaining(Pageable pageRequest, String searchMatcher);

    Page<Article> findByCategoryAndContentContaining(Pageable pageRequest, Category category, String searchMatcher);

    Page<Article> findByCategoryAndTitleContaining(Pageable pageRequest, Category category, String searchMatcher);

    Page<Article> findByTitleContaining(Pageable pageRequest, String searchMatcher);

    List<Article> findByUser(User user);

    List<Article> findTop10ByOrderByCreateAtDesc();

}