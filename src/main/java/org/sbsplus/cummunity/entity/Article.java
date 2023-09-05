package org.sbsplus.cummunity.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.sbsplus.type.Subject;
import org.sbsplus.user.entity.User;
import org.sbsplus.util.Datetime;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Article extends Datetime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    private User author;
    
    @OneToMany
    private List<Comment> comments;
    
    @Enumerated(EnumType.STRING)
    private Subject categoty;
    
    private String title;
    
    private String content;
    
    private int hit;
}
