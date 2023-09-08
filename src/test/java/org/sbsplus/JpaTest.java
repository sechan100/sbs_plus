package org.sbsplus;

import org.junit.jupiter.api.Test;
import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.cummunity.entity.like.ArticleLike;
import org.sbsplus.cummunity.entity.like.Like;
import org.sbsplus.cummunity.repository.ArticleRepository;
import org.sbsplus.type.Subject;
import org.sbsplus.user.entity.User;
import org.sbsplus.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@SpringBootTest
public class JpaTest {
    
    @Autowired
    ArticleRepository articleRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Test
    public void articleAndLike(){
        
        User user_a = User.builder()
                .point(0)
                .email("a@a")
                .name("a")
                .nickname("a")
                .password("{bcrypt}$2a$10$S8HMg0qdJiU.EYWKmzt/lec4TIam7.MAwIvYfOcYbGqIjKCLWv8s2")
                .role("USER")
                .subject(Subject.IT)
                .username("a")
                .build();
        userRepository.save(user_a);
                
        
        
        Article article = Article.builder()
                .title("제목1")
                .content("내용1")
                .hit(1)
                .user(user_a)
                .categoty(Subject.IT)
                .build();
        
        
        Like like = ArticleLike.builder()
                .userId(1)
                .build();
        
        article.setLike(new ArrayList<>());
        article.getLike().add((ArticleLike)like);
        
        articleRepository.save(article);
    }
}
