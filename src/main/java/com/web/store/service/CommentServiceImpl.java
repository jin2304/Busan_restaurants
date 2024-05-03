package com.web.store.service;

import com.web.store.dao.CommentDao;
import com.web.store.dao.StoreDao;
import com.web.store.entity.Comment;
import com.web.store.service.Interface.CommentService;
import org.springframework.stereotype.Service;


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
}
