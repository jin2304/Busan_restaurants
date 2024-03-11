package com.web.store.dto;

import com.web.store.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


/* 로그인 검증 로직 */
public class CustomUserDetails implements UserDetails {

    private Member findUser;

    public CustomUserDetails(Member findUser) {
        this.findUser = findUser;
    }



    //사용자의 특정 권한 확인
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {
                return findUser.getRole();
            }
        });

        return collection;
    }


    @Override
    public String getPassword() {
        return findUser.getPassword();
    }

    @Override
    public String getUsername() {
        return findUser.getUsername();
    }


    public int getUserId() {
        return findUser.getUserId();
    }






    //임시 설정
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
