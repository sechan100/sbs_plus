package org.sbsplus.cummunity.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.sbsplus.type.Subject;
import org.sbsplus.user.entity.User;
import org.sbsplus.util.Datetime;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter @Setter
public class Article extends Datetime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @OneToMany
    private List<Comment> comments;
    
    @Enumerated(EnumType.STRING)
    private Subject categoty;
    
    private String title;
    
    private String content;
    
    private int hit;
}
