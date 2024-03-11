package com.web.store.utils;



public class Pagination {

    public static PageInfo getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit){
        
        int maxPage = (int)Math.ceil((double)listCount / boardLimit);
        //maxPage = 12/5 = 3
        int startPage = (currentPage-1) / pageLimit * pageLimit + 1;
        int endPage = startPage + pageLimit - 1;



        if(endPage > maxPage) endPage = maxPage;

        return new PageInfo(listCount, currentPage,  boardLimit, pageLimit, maxPage, startPage, endPage);
    }


}
