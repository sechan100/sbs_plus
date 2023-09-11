package org.sbsplus.qna.repository;

import org.sbsplus.qna.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}