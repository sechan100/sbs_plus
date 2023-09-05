package org.sbsplus.cummunity.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.sbsplus.type.LikeTargetType;
import org.sbsplus.user.entity.User;

@Entity
@Data
public class like {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Enumerated(EnumType.STRING)
    private LikeTargetType targetType;
    
    private Integer userId;
    
    private Integer targetId;
    
}
