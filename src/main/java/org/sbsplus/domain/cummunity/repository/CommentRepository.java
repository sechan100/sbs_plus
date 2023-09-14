package org.sbsplus.domain.cummunity.repository;

import org.sbsplus.domain.cummunity.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
