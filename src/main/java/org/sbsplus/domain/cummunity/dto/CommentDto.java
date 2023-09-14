package org.sbsplus.domain.cummunity.dto;

import lombok.Data;
import org.sbsplus.domain.cummunity.entity.like.Like;
import org.sbsplus.domain.user.entity.User;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDto {
    
    private Integer id;
    
    private User user;
    
    private List<Like> likes = new ArrayList<>();
    
    private String content;
}
