package org.sbsplus.util;

import org.sbsplus.domain.cummunity.entity.Article;
import org.sbsplus.domain.cummunity.entity.Comment;
import org.sbsplus.domain.cummunity.entity.like.ArticleLike;
import org.sbsplus.domain.cummunity.entity.like.CommentLike;
import org.sbsplus.domain.cummunity.repository.ArticleRepository;
import org.sbsplus.domain.qna.entity.Answer;
import org.sbsplus.domain.qna.entity.Question;
import org.sbsplus.domain.qna.repository.AnswerRepository;
import org.sbsplus.domain.qna.repository.QuestionRepository;
import org.sbsplus.general.type.Category;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;


@Component
public class DataCreator {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    // 생성되는 유저 개수
    protected Integer userNum = 10;

    // 생성되는 게시글 개수
    protected Integer articleNum = 500;

    // 생성되는 question 개수
    protected Integer questionNum = 100;

    // 생성되는 answer 개수
    protected Integer answerNum = 100;

    // 생성되는 게시글 댓글 개수
    protected Integer commentNum = 1000;

    // 생성되는 게시글 좋아요 개수
    protected Integer articleLikeNum = 1000;

    // 생성되는 댓글 좋아요 개수
    protected Integer commentLikeNum = 1000;


    Random random = new Random();

    public void createTestData(){
        createUser();
        createArticles();
        createArticleComments();
        createArticleLike();
        createCommentLike();
        createQuestions();
        createAnswers();
    }

    public void createUser() {
        for(int i = 0; i < userNum; i++){
            char asciiChar = (char) (i + 97);
            String username = String.valueOf(asciiChar);

            User user = new User();
                user.setUsername(username);
                user.setPassword(passwordEncoder.encode(username));
                user.setNickname("유저 " + username);
                user.setName(username);
                user.setEmail(username + "@" + username);
                user.setCategory(Category.IT);
                user.setRole("USER");
                user.setPoint(random.nextInt(50));
                user.setAccumulatedPoint(random.nextInt(100));
            userRepository.save(user);
        }
    }
    
    public void createArticles(){
        for(int i = 0; i < articleNum; i++){
            Article article = new Article();
            article.setUser(randomUser());
            article.setCategory(randomCategory());
            article.setTitle("게시글 " + (i+1) + "번");
            article.setContent("내용내용내용내용내용내용내용");
            article.setHit(random.nextInt(100));
            
            articleRepository.save(article);
        }
        
    }

    public void createArticleComments(){

        for(int i = 0; i < commentNum; i++){
            Comment comment = new Comment();
                comment.setUser(randomUser());
                comment.setContent((i+1) + "번  댓글 내용");

            Article article = randomArticle();
            article.getComments().add(comment);


            articleRepository.save(article);
        }

    }

    public void createArticleLike(){
        for(int i = 0; i < articleLikeNum; i++){
            ArticleLike like = new ArticleLike();
                like.setUser(randomUser());

            Article article = randomArticle();
            article.getLikes().add(like);


            articleRepository.save(article);
        }
    }

    public void createCommentLike(){
        for(int i = 0; i < commentLikeNum; i++){
            CommentLike like = new CommentLike();
                like.setUser(randomUser());

            Article article = randomArticle();
            List<Comment> comments = article.getComments();
            if(!comments.isEmpty()) {
                Comment comment = comments.get(random.nextInt(comments.size()));
                comment.getLikes().add(like);


                articleRepository.save(article);
            }
        }
    }

    public void createQuestions(){

        for(int i = 0; i < questionNum; i++){
            Question question = new Question();
            question.setUser(randomUser());
            question.setPoint(random.nextInt(50)+1);
            question.setSubject("질문" + (i+1) + "번");
            question.setCategory(randomCategory());
            question.setContent("내용내용내용내용내용내용내용");

            questionRepository.save(question);
        }
    }
// 수정
    public void createAnswers(){

        for(int i = 0; i < answerNum; i++){
            Answer answer = new Answer();
            answer.setContent("내용" + (i+1) + "번");
            answer.setUser(randomUser());
            answer.setAccepted(false);
            Question question = questionRepository.findById(random.nextInt(questionNum)+1).orElseThrow();
            question.getAnswers().add(answer);

            questionRepository.save(question);
        }
    }

    protected Category randomCategory(){
        switch(random.nextInt(7) + 1) {
            case 1 -> {
                return Category.CERTIFICATE;
            }
            case 2 -> {
                return Category.MAYA;
            }
            case 3 -> {
                return Category.INTERIOR;
            }
            case 4 -> {
                return Category.IT;
            }
            case 5 -> {
                return Category.DTP;
            }
            case 6 -> {
                return Category.PRODUCT;
            }
            case 7 -> {
                return Category.DESIGN;
            }
            default ->  {
                return null;
            }
        }
    }

    protected User randomUser(){
        return userRepository.findById(random.nextLong(userNum) + 1).orElse(null);
    }

    protected Article randomArticle(){
        return articleRepository.findById(random.nextInt(articleNum) + 1).orElse(null);
    }

}
