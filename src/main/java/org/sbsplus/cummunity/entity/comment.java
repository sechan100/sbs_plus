package org.sbsplus.cummunity.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.sbsplus.user.entity.User;

@Entity
@Data
public class comment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private User author;
    
    private Integer articleId;
    
    private String content;
    
    private String created_at;
    
    private String updated_at;
}
