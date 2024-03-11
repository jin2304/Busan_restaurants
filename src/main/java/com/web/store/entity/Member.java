package com.web.store.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Member {

    private int userId;
    private String username;
    private String password;
    private String role;
}
