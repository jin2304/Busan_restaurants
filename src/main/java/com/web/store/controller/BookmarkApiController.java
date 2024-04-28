package com.web.store.controller;

import com.web.store.dto.BookmarkCheckDto;
import com.web.store.dto.CustomUserDetails;
import com.web.store.entity.Bookmark;
import com.web.store.service.Interface.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/store")
public class BookmarkApiController {

    private StoreService storeService;

    @Autowired
    public BookmarkApiController( StoreService storeService) {
        this.storeService = storeService;
    }


    /**
     * 맛집 북마크 확인
     **/
    @GetMapping("/bookmark/check/{ucSeq}")
    public ResponseEntity<Integer> bookmarkCheck(@PathVariable final int ucSeq){

        // -> AOP 처리?
        // 현재 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication = " + authentication);

        // 사용자가 로그인되어 있는지 확인
        if (authentication instanceof AnonymousAuthenticationToken) {
            // 사용자가 로그인되지 않은 경우, 로그인 페이지로 리디렉션
            System.out.println("로그인 되지 않았습니다");

            /*return 0;*/
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(0); // 로그인되지 않음을 클라이언트에 반환
        }


        // 사용자의 ID 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();

        // 해당 유저가 해당 가게를 북마크했는지 여부를 서비스에서 확인하여 반환
        BookmarkCheckDto bookmarkCheckDto = new BookmarkCheckDto(userId,ucSeq);
        System.out.println("bookmarkCheckDto = " + bookmarkCheckDto);

        int result = storeService.checkBookmark(bookmarkCheckDto);
        System.out.println("result = " + result);


        return ResponseEntity.ok(result);
    }


    /**
     * 맛집 북마크 조회
     **/
    @GetMapping("/bookmark/list/{userId}")
    public List<Bookmark> bookmarkList(@PathVariable final int userId){

        System.out.println("userId = " + userId);
        List<Bookmark> bookmarks = storeService.selectBookmarkList(userId);



        return bookmarks;
        /*return ResponseEntity.ok(1);*/
    }



    /**
     * 맛집 북마크 추가
     **/
    @GetMapping(value ="/bookmark/toggle/{bookmarkId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Integer> bookmark(@PathVariable final int bookmarkId){

        // 현재 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 현재 사용자의 UserDetails 객체 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        // 사용자의 ID 가져오기
        int userId = userDetails.getUserId();


        System.out.println("서버 북마크 호출됨");
        System.out.println("userId = " + userId);System.out.println("bookmarkId = " + bookmarkId);

        Bookmark bookmark = new Bookmark(userId, bookmarkId);
        int result = storeService.insertBookmark(bookmark);

        return ResponseEntity.ok(result);
        /*return 1;*/
    }

    /**
     * 맛집 북마크 삭제
     **/
    //@ResponseBody x
    @GetMapping(value = "/bookmark/delete/{bookmarkId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Integer> removeBookmark(@PathVariable final int bookmarkId) {

        // 사용자의 ID 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


      /*  // 사용자가 로그인되어 있는지 확인
        if (authentication instanceof AnonymousAuthenticationToken) {
            // 사용자가 로그인되지 않은 경우, 로그인 페이지로 리디렉션
            System.out.println("로그인 되지 않았습니다");

            *//*return 0;*//*
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(0); // 로그인되지 않음을 클라이언트에 반환
        }*/


        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();

        Bookmark bookmark = new Bookmark(userId, bookmarkId);
        int result = storeService.deleteBookmark(bookmark);

        return ResponseEntity.ok(result);
    }


}
