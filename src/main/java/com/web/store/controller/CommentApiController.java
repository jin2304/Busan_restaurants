package com.web.store.controller;

//import com.web.store.dto.CommentDto;
import com.web.store.dto.Response.CommentResponse;
import com.web.store.entity.Bookmark;
import com.web.store.entity.Member;
import com.web.store.service.Interface.CommentService;
import com.web.store.dto.CustomUserDetails;
import com.web.store.entity.Comment;
import com.web.store.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController //@Controller + @ResponseBody
//@RequiredArgsConstructor
public class CommentApiController {

    private final MemberService memberService;
    private final CommentService commentService;

    @Autowired
    public CommentApiController(MemberService memberService, CommentService commentService) {
        this.memberService = memberService;
        this.commentService = commentService;
    }


    /**
     * 댓글 등록
     */
    @PostMapping("store/{store_ucSeq}/comments") //계층구조는 슬래시(/) -> store안에 댓글 등록
    public ResponseEntity<Integer> saveComment(@PathVariable final int store_ucSeq, @RequestBody Map<String, String> requestBody /*@PathVariable final int userId , @RequestBody final CommentDto params*/){

        String content = requestBody.get("content");
        System.out.println("content:" + content);
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
        System.out.println("userId:" +  userId);



        //1번 방법
        Member member = memberService.findByUserId(userId);
        //System.out.println("member:" + member);
        //Comment comment = new Comment(store_ucSeq, userId, content, member.getNickname());
        
        //2번 방법
        Comment comment = new Comment(userId, store_ucSeq, content);

        int result = commentService.insertComment(comment);

        return ResponseEntity.ok(1);

    }


    /**
     * 댓글 조회
     * */
    @GetMapping("store/{store_ucSeq}/comments")
    public List<CommentResponse> findAllComment(@PathVariable final int store_ucSeq){
        List<CommentResponse> comments = commentService.findAllComment(store_ucSeq);

        for(CommentResponse comment : comments){
            System.out.println(comment);
        }

        return comments;//JSON 형태로 객체 리스트 반환
    }


}
