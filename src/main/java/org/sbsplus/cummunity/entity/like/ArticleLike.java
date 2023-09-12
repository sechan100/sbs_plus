package org.sbsplus.cummunity.entity.like;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sbsplus.cummunity.entity.Article;
import org.sbsplus.user.entity.User;

@Entity
@DiscriminatorValue("article")
@NoArgsConstructor
public class ArticleLike extends Like {
    public ArticleLike(User user){
        super(user);
    }
}
