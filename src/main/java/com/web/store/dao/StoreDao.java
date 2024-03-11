package com.web.store.dao;

import com.web.store.dto.BookmarkCheckDto;
import com.web.store.dto.BookmarkDto;
import com.web.store.entity.Bookmark;
import com.web.store.entity.Store;
import com.web.store.utils.PageInfo;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface StoreDao {

    int insertStore(Store store);

    int selectListCount();

    Store selectStoreDetail(int unSeq);

    List<Store> selectStoreList(PageInfo pageInfo, RowBounds rowBounds);

    //List<Store> selectList(PageInfo pageInfo, RowBounds rowBounds);



    int increaseCount(int ucSeq);

    Store selectStore(int ucSeq);

    int deleteStore(int ucSeq);

    int updateBoard(Store store);

    
    
    
    
    
    //북마크 추가
    int insertBookmark(Bookmark bookmark);

    //북마크 삭제
    int deleteBookmark(Bookmark bookmark);

    //List<BookmarkDto> selectBookmarkStore(List<Bookmark> bookmarks);
    List<BookmarkDto> selectBookmarkStore(int userId);

    List<Bookmark> selectBookmarkList(int userId);
    
    //북마크 확인
    int checkBookmark(BookmarkCheckDto bookmarkCheckDto);


}
