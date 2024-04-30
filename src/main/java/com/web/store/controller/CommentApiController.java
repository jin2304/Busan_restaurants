package com.web.store.controller;

//import com.web.store.dto.CommentDto;
import com.web.store.dto.CustomUserDetails;
import com.web.store.entity.Member;
import com.web.store.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController //@Controller + @ResponseBody
@RequestMapping("/store")
//@RequiredArgsConstructor
public class CommentApiController {

    private final MemberService memberService;

    @Autowired
    public CommentApiController( MemberService memberService) {
        this.memberService = memberService;
    }

    //댓글 등록
    @PostMapping("/{store_ucSeq}/comments") //계층구조는 슬래시(/) -> store안에 댓글 등록
    public ResponseEntity<Integer> saveComment(@PathVariable final int store_ucSeq /*@PathVariable final int userId , @RequestBody final CommentDto params*/){

        System.out.println("store_ucSeq:" + store_ucSeq);
        // AOP 처리?
        // 사용자의 ID 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication = " + authentication);

        // 사용자가 로그인되어 있는지 확인
        if (authentication instanceof AnonymousAuthenticationToken) {
            // 사용자가 로그인되지 않은 경우, 로그인 페이지로 리디렉션
            System.out.println("로그인 되지 않았습니다");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(0);
        }

        // 사용자의 ID 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();
        System.out.println("usrId:" +  userId);


        //사용자ID로 사용자정보(닉네임) 조회
        Member member = memberService.findByUserId(userId);
        System.out.println("member:" + member);

        return ResponseEntity.ok(1);

    }


}

