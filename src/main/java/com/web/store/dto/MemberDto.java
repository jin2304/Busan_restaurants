package com.web.store.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {

    private int userId;
    private String username;
    private String password;
    private String role;

}
