package org.sbsplus.cummunity.entity.like;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@DiscriminatorValue("article")
public class ArticleLike extends Like{
}
