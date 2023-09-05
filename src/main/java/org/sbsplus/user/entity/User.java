package org.sbsplus.user.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.sbsplus.type.Subject;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String username;

    private String password;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    private String role;

    private int point;
}