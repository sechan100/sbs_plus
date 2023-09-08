package org.sbsplus.cummunity.entity.like;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("comment")
public class CommentLike extends Like{
    
    CommentLike(Integer id, Integer userId) {
        super(id, userId);
    }
}
