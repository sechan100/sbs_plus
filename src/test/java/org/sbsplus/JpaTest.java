package org.sbsplus;

import org.junit.jupiter.api.Test;
import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.cummunity.repository.ArticleRepository;
import org.sbsplus.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JpaTest {
    
    @Autowired
    ArticleRepository articleRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Test
    public void articleInsert(){
        Article article1 = new Article();
    }

}
