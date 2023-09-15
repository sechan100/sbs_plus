package org.sbsplus.domain.user.dto;


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
    private String category;

    private String role;

    private int point;

    private boolean suspended;
}