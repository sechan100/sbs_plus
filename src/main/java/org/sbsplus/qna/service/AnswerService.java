package org.sbsplus.qna.service;

import lombok.RequiredArgsConstructor;
import org.sbsplus.qna.entity.Answer;
import org.sbsplus.qna.entity.Question;
import org.sbsplus.qna.repository.AnswerRepository;
import org.sbsplus.qna.repository.QuestionRepository;
import org.sbsplus.user.entity.User;
import org.sbsplus.user.service.UserService;
import org.sbsplus.util.Rq;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    private final UserService userService;

    private final Rq rq;

    public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setUser(rq.getUser());
        question.getAnswers().add(answer);
        questionRepository.save(question);
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
                userService.save(answerAuthor); // 사용자 업데이트
                userService.save(questionAuthor); // 사용자 업데이트

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