package com.web.store.controller;


import com.web.store.dto.BookmarkDto;
import com.web.store.dto.CustomUserDetails;
import com.web.store.dto.MemberDto;
import com.web.store.entity.Bookmark;
import com.web.store.service.Interface.StoreService;
import com.web.store.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MemberController {


    @Autowired
    MemberService memberService;

    @Autowired
    StoreService storeService;


    @GetMapping("/join")
    public String join() {
        System.out.println("ㅡㅡㅡ join ㅡㅡㅡ");
        return "/member/join";
    }


    @PostMapping("/joinProc")
    public String joinProcess(MemberDto member) {
        System.out.println("ㅡㅡㅡ joinProc ㅡㅡㅡ");
        System.out.println("member = " + member);

        memberService.joinProcess(member);

        return "redirect:/login";
    }



    @GetMapping("/login")
    public String login() {

        return "/member/login";
    }



/*    @PostMapping("/myPage/{userId}")
    public String myPage(@PathVariable int userId) {

                        *//* memberService.selectMember(userId);*//*

        return "/member/myPage";
    }*/


    @GetMapping("/myPage")
    public String myPage(Model model) {

        // 현재 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자가 로그인되어 있는지 확인
        if (authentication instanceof AnonymousAuthenticationToken) {
            // 사용자가 로그인되지 않은 경우, 로그인 페이지로 리디렉션
            return "redirect:/login";
        }

        // 현재 사용자의 UserDetails 객체 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();


        // 사용자의 ID 가져오기
        int userId = userDetails.getUserId();
        System.out.println("userId = " + userId);


        // 조인된 북마크 가져오기(Bookmark-store)
        List<BookmarkDto> bookmarkStores =  storeService.selectBookmarkStore(userId);

        for(BookmarkDto bookmark : bookmarkStores){
            System.out.println("bookmark = " + bookmark);
        }


        model.addAttribute("userId", userId);
        model.addAttribute("bookmarkStores", bookmarkStores);
        return "/member/myPage";
    }


    @GetMapping("/good")
    public String good() {

        return "good";
    }


}