package com.web.store.dao;

import com.web.store.entity.Comment;

import java.util.List;

public interface CommentDao {
    int insertComment(Comment comment);

    List<Comment> findAllComment(int store_ucSeq);

}
