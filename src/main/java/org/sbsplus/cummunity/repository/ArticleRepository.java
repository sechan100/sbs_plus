package org.sbsplus.cummunity.repository;

import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.type.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Page<Article> findByCategory(Pageable pageRequest, Category category);
    
    @EntityGraph(attributePaths = {"comments", "likes"})
    Optional<Article> findById(Integer id);
}
