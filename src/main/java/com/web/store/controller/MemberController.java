package com.web.store.controller;


import com.web.store.dto.BookmarkDto;
import com.web.store.dto.CustomUserDetails;
import com.web.store.dto.MemberDto;
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


    private final MemberService memberService;
    private final StoreService storeService;

    @Autowired
    public MemberController(MemberService memberService, StoreService storeService) {
        this.memberService = memberService;
        this.storeService = storeService;
    }



    /**
     *  회원가입 페이지
     */
    @GetMapping("/join")
    public String join() {
        return "/member/join";
    }


    /**
     *  회원가입
     */
    @PostMapping("/joinProc")
    public String joinProcess(MemberDto member) {
        memberService.joinProcess(member);
        return "redirect:/login";
    }


    /**
     *  로그인 페이지
     */
    @GetMapping("/login")
    public String login() {
        return "/member/login";
    }


    /**
     *  마이페이지
     */
    @GetMapping("/myPage")
    public String myPage(Model model) {

        // 현재 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자가 로그인되어 있는지 확인
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login"; // 사용자가 로그인되지 않은 경우, 로그인 페이지로 리디렉션
        }

        // 사용자의 ID 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();
        // 조인된 북마크 가져오기(Bookmark-store)
        List<BookmarkDto> bookmarkStores =  storeService.selectBookmarkStore(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("bookmarkStores", bookmarkStores);
        return "/member/myPage";
    }
}