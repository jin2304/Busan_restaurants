package com.web.store.dao;


import com.web.store.dto.MemberDto;
import com.web.store.entity.Member;

//DB에 단일 접근
public interface MemberDao {

    //Member loginMember(Member m);
    public void joinProcess(MemberDto member);

    public Member findByUsername(String username);


}
