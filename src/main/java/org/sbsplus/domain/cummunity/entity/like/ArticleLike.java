package org.sbsplus.domain.cummunity.entity.like;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.sbsplus.domain.user.entity.User;

@Entity
@DiscriminatorValue("article")
@NoArgsConstructor
public class ArticleLike extends Like {
    public ArticleLike(User user){
        super(user);
    }
}
