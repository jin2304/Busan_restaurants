package com.web.store.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Comment {
    private int user_userId;
    private int store_ucSeq;
    private String content;

    public Comment(int user_userId, int store_ucSeq, String content) {
        this.user_userId = user_userId;
        this.store_ucSeq = store_ucSeq;
        this.content = content;
    }
}
