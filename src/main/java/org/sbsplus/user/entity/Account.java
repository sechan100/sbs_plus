package org.sbsplus.user.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.sbsplus.subject.Subject;

@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String username;

    private String password;

    private String name;

    // 6 ~ 7자 내외..
    private String nickname;

    private String email;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    // phone number format: ex) 010-1111-2222
    // Needed 'auto formater' in frontEnd using html and JS..
    private String phone;

    private String role;

    private int point;
}