package org.sbsplus.domain.qna.service;

import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.admin.AdminService;
import org.sbsplus.domain.cummunity.entity.Article;
import org.sbsplus.domain.qna.entity.Answer;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.domain.qna.repository.QuestionRepository;
import org.sbsplus.domain.qna.DataNotFoundException;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.user.service.UserService;
import org.sbsplus.general.type.Category;
import org.sbsplus.util.Rq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserService userService;
    private final AdminService adminService;
    private final Rq rq;

    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true); //중복 제거
                Join<Question, User> u1 = q.join("user", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answers", JoinType.LEFT);
                Join<Answer, User> u2 = a.join("user", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"),  //제목
                        cb.like(q.get("content"), "%" + kw + "%"),  //내용
                        cb.like(u1.get("username"), "%" + kw + "%"), //질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"), //답변 내용
                        cb.like(a.get("user"), "%" + kw + "%")); //답변 작성자
            }
        };
    }
    public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createAt"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = search(kw);
        return this.questionRepository.findAll(spec, pageable);
    }
    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if(question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, int point, String category) {
       Category c = Category.convertStringToEnum(category);
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCategory(c);
        q.setPoint(point);
        q.setUser(rq.getUser());
        this.questionRepository.save(q);
    }

    public void modify(Question question, String subject, String content, Category category) {
        question.setSubject(subject);
        question.setContent(content);
        question.setCategory(category);
        this.questionRepository.save(question);
    }

    @Transactional
    public boolean checkQuestionOwnership(Integer questionId){

        Question question = questionRepository.findById(questionId).orElseThrow();


        // admin도 수정은 못함
        if(rq.getUser() == null){
            return false;
        }



        User user_ = rq.getUser();
        User user = userService.findByUsername(user_.getUsername());

        return question.getUser() == user;
    }
    public void delete(Integer id) throws AccessDeniedException{
        if(checkQuestionOwnership(id) || adminService.isAdmin()){
            questionRepository.deleteById(id);
        } else {
            throw new AccessDeniedException("게시물에 대한 접근 권한이 없습니다.");
        }
    }

    public List<Question> findByUser(User user) {


        return questionRepository.findByUser(user);
    }
}