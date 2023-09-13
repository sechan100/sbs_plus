package org.sbsplus.cummunity.entity.like;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.sbsplus.user.entity.User;

@Entity
@DiscriminatorValue("comment")
@NoArgsConstructor
public class CommentLike extends Like {
    public CommentLike(User user){
        super(user);
    }
}
