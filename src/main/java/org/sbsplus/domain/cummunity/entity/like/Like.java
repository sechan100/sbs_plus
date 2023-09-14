package org.sbsplus.domain.cummunity.entity.like;

import jakarta.persistence.*;
import lombok.*;
import org.sbsplus.domain.user.entity.User;

import static jakarta.persistence.GenerationType.*;
import static jakarta.persistence.InheritanceType.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "likes")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "target_type")
public class Like {
    
    public Like(User user){
        this.user = user;
    }
    
    
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;
    
}
