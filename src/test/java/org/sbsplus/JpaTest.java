package org.sbsplus;

import org.junit.jupiter.api.Test;
import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.cummunity.repository.ArticleRepository;
import org.sbsplus.type.Subject;
import org.sbsplus.user.entity.User;
import org.sbsplus.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
public class JpaTest {
    
    @Autowired
    ArticleRepository articleRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Test
    public void articleWrite(){
        
        Optional<User> author_ = userRepository.findById(1);
        User author = author_.orElse(null);
        
        Article article = new Article();
            article.setAuthor(author);
            article.setCategoty(Subject.INTERIOR);
            article.setTitle("제목제목");
            article.setContent("내용내용내용내용내용");
            article.setHit(1);
        
        
        articleRepository.save(article);
    }
}
