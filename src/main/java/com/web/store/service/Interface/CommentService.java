package com.web.store.service.Interface;

import com.web.store.dto.Request.CommentRequest;
import com.web.store.dto.Response.CommentResponse;
import com.web.store.entity.Comment;

import java.util.List;

public interface CommentService {
    int insertComment(Comment comment);

    List<CommentResponse> findAllComment(int store_ucSeq);

    int deleteComment(int commentId);

    CommentResponse findCommentById(int commentId);

    int updateComment(CommentRequest params);
}
