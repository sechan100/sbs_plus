package org.sbsplus.cummunity.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.sbsplus.user.entity.User;

@Entity
@Data
public class Comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    private User author;
    
    private Integer articleId;
    
    private String content;
    
    private String created_at;
    
    private String updated_at;
}
