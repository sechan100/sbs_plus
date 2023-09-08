package org.sbsplus.user.entity;


import jakarta.persistence.*;
import lombok.*;
import org.sbsplus.type.Subject;

@Getter @Setter
@Entity
@NoArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;

    private String password;

    private String nickname;
    
    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    private String role;

    private int point;
    
    private int accumulatedPoint;
}