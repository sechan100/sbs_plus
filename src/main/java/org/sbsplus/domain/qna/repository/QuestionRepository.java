package org.sbsplus.domain.qna.repository;

import org.sbsplus.domain.cummunity.entity.Article;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    @Override
    Page<Question> findAll(Pageable pageable);

    List<Question> findTop10ByOrderByCreateAtDesc();

    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    List<Question> findByUser(User user);
}