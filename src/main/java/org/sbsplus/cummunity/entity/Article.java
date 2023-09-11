package org.sbsplus.cummunity.entity;


import jakarta.persistence.*;
import lombok.*;
import org.sbsplus.cummunity.entity.like.Like;
import org.sbsplus.type.Category;
import org.sbsplus.user.entity.User;
import org.sbsplus.util.Datetime;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@EqualsAndHashCode(callSuper = true)
@Getter @Setter
@Entity
@NoArgsConstructor
public class Article extends Datetime {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "article_id")
    private List<Comment> comments = new ArrayList<>();
    
    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "target_id")
    private List<Like> likes = new ArrayList<>();
    
    @Enumerated(STRING)
    private Category category;
    
    private String title;
    
    private String content;
    
    private int hit;
}
