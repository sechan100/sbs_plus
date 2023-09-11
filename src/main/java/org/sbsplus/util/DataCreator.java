package org.sbsplus.util;

import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.cummunity.repository.ArticleRepository;
import org.sbsplus.type.Category;
import org.sbsplus.user.entity.User;
import org.sbsplus.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class DataCreator {
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    ArticleRepository articleRepository;
    
    Random random = new Random();
    
    
    public void createTestData(){
        createUser();
        createArticles();
    }
    
    
    public void createUser() {
        
        
        for(int i = 0; i < 5; i++){
            
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
        
        for(int i = 0; i < 200; i++){
            Article article = new Article();
            article.setUser(userRepository.findById(random.nextInt(5) + 1).orElse(null));
            article.setCategory(intCategoryMatcher(random.nextInt(7) + 1));
            article.setTitle("게시글 " + (i+1) + "번");
            article.setContent("내용내용내용내용내용내용내용");
            article.setHit(random.nextInt(100));
            
            articleRepository.save(article);
        }
        
    }
    
    
    
    protected Category intCategoryMatcher(Integer num){
        switch(num) {
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
    
    
}
