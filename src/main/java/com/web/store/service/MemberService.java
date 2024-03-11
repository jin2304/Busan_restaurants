package com.web.store.service;


import com.web.store.dto.MemberDto;
import com.web.store.entity.Member;
import com.web.store.dao.MemberDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


//서비스 로직, 트랜잭션 처리
@Service
public class MemberService {

    private final MemberDao memberDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public MemberService(MemberDao memberDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.memberDao = memberDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    public void joinProcess(MemberDto member) {
        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));
        member.setRole("ROLE_ADMIN");
        memberDao.joinProcess(member);
    }



    public Member findByUsername(String username){
        return memberDao.findByUsername(username);
    }

}