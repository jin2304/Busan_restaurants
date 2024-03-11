package com.web.store.utils;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

//페이지 정보
@Getter
@RequiredArgsConstructor
public class PageInfo {

    private int listCount;
    private int currentPage;
    private int boardLimit;
    private int pageLimit;
    private int maxPage;
    private int startPage;
    private int endPage;

    public PageInfo(int listCount, int currentPage, int boardLimit, int pageLimit, int maxPage, int startPage, int endPage) {
        this.listCount = listCount;
        this.currentPage = currentPage;
        this.boardLimit = boardLimit;
        this.pageLimit = pageLimit;
        this.maxPage = maxPage;
        this.startPage = startPage;
        this.endPage = endPage;
    }
}
