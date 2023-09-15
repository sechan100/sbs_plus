package org.sbsplus.domain.qna.service;

import lombok.RequiredArgsConstructor;
import org.sbsplus.domain.qna.DataNotFoundException;
import org.sbsplus.domain.qna.repository.AnswerRepository;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.qna.entity.Answer;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.domain.qna.repository.QuestionRepository;
import org.sbsplus.domain.user.repository.UserRepository;
import org.sbsplus.domain.user.service.UserService;
import org.sbsplus.util.Rq;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final Rq rq;

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }
    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setUser(rq.getUser());
        question.getAnswers().add(answer);
        questionRepository.save(question);
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        this.answerRepository.save(answer);
    }

    public void markAnswerAsAccepted(Integer answerId, Integer questionId) {
        Answer answer = answerRepository.findById(answerId).orElse(null);
        if (answer != null) {
            answer.setAccepted(true); // 답변을 채택으로 표시
            answerRepository.save(answer);

            Question question = questionRepository.findById(questionId).orElse(null);
            if (question != null) {
                question.setAcceptedAnswer(answer); // 질문에 채택된 답변 설정
                questionRepository.save(question);
            }
            User answerAuthor = answer.getUser(); // 답변 작성자assert question != null;
            User questionAuthor = question.getUser();

            if (answerAuthor != null) {
                int awardedPoints = question.getPoint(); // 포인트 지급량 (원하는 값으로 변경)

                int answerAuthorBeforePoint = answerAuthor.getPoint();
                int questionAuthorBeforePoint = questionAuthor.getPoint();

                answerAuthor.addPoints(awardedPoints); // 포인트 추가 메소드 호출
                questionAuthor.subtractPoints(awardedPoints);
                userRepository.save(answerAuthor); // 사용자 업데이트
                userRepository.save(questionAuthor); // 사용자 업데이트

                int answerAuthorAfterPoint = answerAuthor.getPoint();
                int questionAuthorAfterPoint = questionAuthor.getPoint();

                System.out.println("================================================");
                System.out.println(answerAuthorBeforePoint + " -> " + answerAuthorAfterPoint);
                System.out.println(questionAuthorBeforePoint + " -> " + questionAuthorAfterPoint);
                System.out.println("================================================");


            }
        }
    }
}