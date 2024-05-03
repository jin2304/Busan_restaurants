package com.web.store.service.Interface;

import com.web.store.entity.Comment;

import java.util.List;

public interface CommentService {
    int insertComment(Comment comment);

    List<Comment> findAllComment(int store_ucSeq);
}
