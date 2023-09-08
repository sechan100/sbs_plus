package org.sbsplus.cummunity.entity;


import jakarta.persistence.*;
import lombok.*;
import org.sbsplus.user.entity.User;
import org.sbsplus.util.Datetime;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment extends Datetime {
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    private String content;


}
