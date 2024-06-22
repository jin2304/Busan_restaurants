package com.web.store.dto.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CommentRequest {
    private int commentId;
    private int user_userId;
    private int store_ucSeq;
    private String content;
}
