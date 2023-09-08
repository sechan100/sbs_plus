package org.sbsplus.cummunity.entity.like;

import jakarta.persistence.*;
import lombok.*;
import org.sbsplus.type.LikeTargetType;

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
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    
    private Integer userId;
    
}
