package org.sbsplus.cummunity.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.sbsplus.user.entity.User;
import org.sbsplus.util.Datetime;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Comment extends Datetime {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private Integer articleId;
    
    private String content;
}
