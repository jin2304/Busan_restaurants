package com.web.store.service;

import com.web.store.dao.CommentDao;
import com.web.store.dto.Response.CommentResponse;
import com.web.store.entity.Comment;
import com.web.store.service.Interface.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }



    @Override
    public int insertComment(Comment comment) {
        return commentDao.insertComment(comment);
    }

    @Override
    public List<CommentResponse> findAllComment(int store_ucSeq) {
        return commentDao.findAllComment(store_ucSeq);
    }

    @Override
    public int deleteComment(int commentId) {
        return commentDao.deleteComment(commentId);
    }

    @Override
    public CommentResponse findCommentById(int commentId) {
        return commentDao.findCommentById(commentId);
    }
}
