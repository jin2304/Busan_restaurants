package com.web.store.dao.mybatis;


import com.web.store.dao.MemberDao;
import com.web.store.dto.MemberDto;
import com.web.store.entity.Member;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class MybatisMemberDao implements MemberDao {


    private MemberDao mapper;

    @Autowired
    public MybatisMemberDao(SqlSession sqlSession) {
        mapper = sqlSession.getMapper(MemberDao.class);
    }


    @Override
    public void joinProcess(MemberDto member) {
        mapper.joinProcess(member);
    }

    @Override
    public Member findByUsername(String username) {
        Member findUser = mapper.findByUsername(username);
        System.out.println("Dao: findUser = " + findUser);
        return findUser;
    }






/*    //로그인
    @Override
    public Member loginMember(Member member){
        return mapper.loginMember(member);
        //return session.selectOne("memberMapper.loginMember", member);
    }*/
}
