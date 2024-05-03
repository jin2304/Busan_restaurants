package com.web.store.dao.mybatis;

import com.web.store.dao.CommentDao;
import com.web.store.dto.Response.CommentResponse;
import com.web.store.entity.Comment;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class MybatisCommentDao implements CommentDao {

    private CommentDao mapper;

    @Autowired
    public MybatisCommentDao(SqlSession sqlSession) {
        mapper = sqlSession.getMapper(CommentDao.class);
        //세션을 통해 mapper 컨테이너에서 mapper 객체를 꺼내 씀
    }

    @Override
    public int insertComment(Comment comment) {
        return mapper.insertComment(comment);
    }

    @Override
    public List<CommentResponse> findAllComment(int store_ucSeq) {
        return mapper.findAllComment(store_ucSeq);
    }
}
