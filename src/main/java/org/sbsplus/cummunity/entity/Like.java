package org.sbsplus.cummunity.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.sbsplus.type.LikeTargetType;

@Entity
@Data
@Table(name = "likes")
public class Like {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Enumerated(EnumType.STRING)
    private LikeTargetType targetType;
    
    private Integer userId;
    
    private Integer targetId;
    
}
