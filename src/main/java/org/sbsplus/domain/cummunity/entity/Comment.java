package org.sbsplus.domain.cummunity.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;
import org.sbsplus.domain.cummunity.entity.like.Like;
import org.sbsplus.domain.user.entity.User;
import org.sbsplus.util.Datetime;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment extends Datetime {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;
    
    @OneToMany(cascade = ALL, orphanRemoval = true)
    @JoinColumn(name = "target_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Where(clause = "target_type = 'comment'")
    private List<Like> likes = new ArrayList<>();
    
    private String content;


}
