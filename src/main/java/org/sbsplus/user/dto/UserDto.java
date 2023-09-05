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

    // 6 ~ 7자 내외..
    private String nickname;

    private String email;

    // String type subject
    private String subject;

    // phone number format: ex) 010-1111-2222
    // Needed 'auto formater' in frontEnd using html and JS..
    private String phone;

    private String role;

    private int point;
}