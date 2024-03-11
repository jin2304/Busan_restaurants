package com.web.store.service.Interface;


import com.web.store.dto.BookmarkCheckDto;
import com.web.store.dto.BookmarkDto;
import com.web.store.entity.Bookmark;
import com.web.store.entity.Store;
import com.web.store.utils.PageInfo;

import java.util.ArrayList;
import java.util.List;

public interface StoreService {

    //DB 삽입
    int insertStore(Store store);

    //전체 가게 개수 조회
    int selectListCount();

    //전체 가게 select
    List<Store> selectStoreList(PageInfo pageInfo);


    //가게 상세 정보 select(특정 가게 select)
    Store selectStoreDetail(int unSeq);

    //북마크 확인
    int checkBookmark(BookmarkCheckDto bookmarkCheckDto);

    //북마크 조회
    List<Bookmark> selectBookmarkList(int userId);

    //북마크-상점 select
    List<BookmarkDto> selectBookmarkStore(int userId);
    //List<BookmarkDto> selectBookmarkStore(List<Bookmark> bookmarks);



    //구별로 가게 리스트 select
    //ArrayList<Store> selectStoreList(String gugunNm);


    //북마크 추가
    int insertBookmark(Bookmark bookmark);
    
    //북마크 삭제
    int deleteBookmark(Bookmark bookmark);



}
