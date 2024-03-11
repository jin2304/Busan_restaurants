package com.web.store.service;

import com.web.store.dao.mybatis.MybatisMemberDao;
import com.web.store.dto.CustomUserDetails;
import com.web.store.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailService implements UserDetailsService {

    MybatisMemberDao mybatisMemberDao;

    @Autowired
    public CustomerUserDetailService(MybatisMemberDao mybatisMemberDao) {
        this.mybatisMemberDao = mybatisMemberDao;
    }

    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);


        Member findUser = mybatisMemberDao.findByUsername(username);
        System.out.println("findUser = " + findUser);

        if(findUser != null){
            return new CustomUserDetails(findUser);   //spring security에 전달해서 검증
        }

        return null;
    }
}
