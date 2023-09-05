package org.sbsplus.cummunity.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.sbsplus.type.Subject;
import org.sbsplus.user.entity.User;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
public class article {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private User author;
    
    @Enumerated(EnumType.STRING)
    private Subject categoty;
    
    private String created_at;
    
    private String updated_at;
    
    private String title;
    
    private String content;
    
    private Integer hit;
}
