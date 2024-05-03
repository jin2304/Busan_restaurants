package com.web.store.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CommentResponse {
    private int commentId;
    private int user_userId;
    private int store_ucSeq;
    private String content;
    private int status;
    private String created_date;
    private String modified_date;
}
