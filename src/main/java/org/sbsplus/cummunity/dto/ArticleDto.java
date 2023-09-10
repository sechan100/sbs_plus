package org.sbsplus.cummunity.dto;


import lombok.Data;
import org.sbsplus.cummunity.entity.Comment;
import org.sbsplus.cummunity.entity.like.Like;
import org.sbsplus.type.Category;
import org.sbsplus.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ArticleDto {
    
    private Integer id;
    
    private User user;
    
    private List<Comment> comments;
    
    private List<Like> likes;
    
    private Category category;
    
    private String title;
    
    private String content;
    
    private int hit;
    
    private LocalDateTime createAt;
    
    private LocalDateTime updateAt;
    
    
    
    
}
