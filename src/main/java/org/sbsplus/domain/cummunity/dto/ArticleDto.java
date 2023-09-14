package org.sbsplus.domain.cummunity.dto;


import lombok.Data;
import org.sbsplus.domain.cummunity.entity.Comment;
import org.sbsplus.domain.cummunity.entity.like.Like;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.general.type.Category;

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
