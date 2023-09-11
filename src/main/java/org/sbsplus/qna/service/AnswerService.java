package org.sbsplus.qna.service;

import lombok.RequiredArgsConstructor;
import org.sbsplus.qna.entity.Answer;
import org.sbsplus.qna.entity.Question;
import org.sbsplus.qna.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }
}