package org.sbsplus.domain.qna.repository;

import org.sbsplus.domain.qna.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}