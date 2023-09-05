package org.sbsplus.cummunity.repository;

import org.sbsplus.cummunity.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
}
