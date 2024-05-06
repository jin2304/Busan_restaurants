package com.web.store.dao;

import com.web.store.dto.Response.CommentResponse;
import com.web.store.entity.Comment;

import java.util.List;

public interface CommentDao {
    int insertComment(Comment comment);

    List<CommentResponse> findAllComment(int store_ucSeq);

    int deleteComment(int commentId);

}
