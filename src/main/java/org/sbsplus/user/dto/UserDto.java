package org.sbsplus.user.dto;


import lombok.Data;

@Data
public class UserDto {

    private Integer id;

    private String username;

    private String password;

    // difference with Account Entity
    private String confirmPassword;

    private String name;

    private String email;

    // String type subject
    private String subject;

    private String role;

    private int point;
}