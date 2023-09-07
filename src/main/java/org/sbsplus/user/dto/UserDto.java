package org.sbsplus.user.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {

    private String username;

    private String password;

    // difference with Account Entity
    private String confirmPassword;

    private String name;
    
    private String nickname;

    private String email;

    // String type subject
    private String subject;

    private String role;

    private int point;
}