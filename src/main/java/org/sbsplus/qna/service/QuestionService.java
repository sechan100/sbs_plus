package org.sbsplus.qna.service;

import lombok.RequiredArgsConstructor;
import org.sbsplus.qna.DataNotFoundException;
import org.sbsplus.qna.entity.Question;
import org.sbsplus.qna.repository.QuestionRepository;
import org.sbsplus.type.Category;
import org.sbsplus.util.Rq;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final Rq rq;

    public List<Question> getList() {
        return this.questionRepository.findAll();
    }
    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, String category) {
       Category c = Category.convertStringToEnum(category);
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCategory(c);
        q.setUser(rq.getUser());
        this.questionRepository.save(q);
    }
}