package org.sbsplus.cummunity.repository;

import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.cummunity.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
