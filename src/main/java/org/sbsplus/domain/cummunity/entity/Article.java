package org.sbsplus.domain.cummunity.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.sbsplus.domain.cummunity.entity.like.Like;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.general.type.Category;
import org.sbsplus.util.Datetime;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;

@EqualsAndHashCode(callSuper = false)
@Getter @Setter
@Entity
@NoArgsConstructor
public class Article extends Datetime {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;
    
    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "article_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Comment> comments = new ArrayList<>();
    
    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "target_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Where(clause = "target_type = 'article'")
    private List<Like> likes = new ArrayList<>();
    
    @Enumerated(STRING)
    private Category category;
    
    private String title;
    
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    
    private int hit;
    
    public void increaseHit(){
        this.setHit(this.getHit()+1);
    }
}
