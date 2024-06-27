package com.web.store.service;


import com.web.store.dao.mybatis.MyBatisStoreDao;
import com.web.store.dao.StoreDao;
import com.web.store.dto.BookmarkCheckDto;
import com.web.store.dto.BookmarkDto;
import com.web.store.entity.Bookmark;
import com.web.store.entity.Store;
import com.web.store.service.Interface.StoreService;
import com.web.store.utils.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreDao storeDao; //인터페이스에만 의존
    int count;
    //private final MyBatisStoreDao myBatisStore;

    @Autowired
    public StoreServiceImpl(StoreDao StoreDao) {
        this.storeDao = StoreDao;
        //this.myBatisStore = myBatisStore;
    }

    //가게 전체 삽입(트랜잭션으로 처리) V2  -> @Transactional 통해 스프링이 자동으로 컨트롤러에 예외 전파
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertStores(List<Store> stores) {
        int count = 0;
        for (Store store : stores) {
            storeDao.insertStore(store);
            count++;
        }
        return count;
    }


    //가게 전체 삽입(트랜잭션으로 처리) V1 -> 명시적으로 예외를 전파
/*    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insertStores(List<Store> stores) {
        int count = 0;
        for (Store store : stores) {
            try {
                storeDao.insertStore(store);
                count++;
            } catch (DataAccessException e) {
                throw e;  // 중복 예외 또는 기타 데이터 액세스 예외를 그대로 던짐
                //throw new RuntimeException("Failed2 to insert store: " + e.getMessage(), e);
            }
        }
        return count;
    }*/



    //가게 개별 삽입
//    @Override
//    public int insertStore(Store store) {
//        try {
//            return storeDao.insertStore(store);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to insert store: " + e.getMessage(), e);
//        }
//    }



    @Override
    public int selectListCount() {
        return storeDao.selectListCount();
    }

    @Override
    public List<Store> selectStoreList(PageInfo pageInfo) {

        //게시판 시작 위치 ex)3페이지라면, (3페이지-1)*5=10
        int offset = (pageInfo.getCurrentPage()-1) * pageInfo.getBoardLimit();
        //System.out.println("offset = " + offset);
        //System.out.println("pageInfo.getBoardLimit() = " +  pageInfo.getBoardLimit());
        RowBounds rowBounds = new RowBounds(offset, pageInfo.getBoardLimit()); //RowBounds(10, 5) -> 10부터 5개
        return storeDao.selectStoreList(pageInfo, rowBounds);
    }

    @Override
    public List<Bookmark> selectBookmarkList(int userId) {
        return storeDao.selectBookmarkList(userId);
    }

    /*@Override
    public List<BookmarkDto> selectBookmarkStore(List<Bookmark> bookmarks) {
        return storeDao.selectBookmarkStore(bookmarks);
    }*/

    @Override
    public List<BookmarkDto> selectBookmarkStore(int userId) {
        return storeDao.selectBookmarkStore(userId);
    }
    //가게 상세 정보 select
    @Override
    public Store selectStoreDetail(int unSeq) {
        return storeDao.selectStoreDetail(unSeq);
    }

    @Override
    public int checkBookmark(BookmarkCheckDto bookmarkCheckDto) {
        return storeDao.checkBookmark(bookmarkCheckDto);
    }

    private boolean isDuplicateStore(Store store) {
/*      // StoreDao를 사용하여 중복을 확인
        Store existingStore = StoreDao.selectStoreByTitle(store.getMainTitle());
        // 중복 여부를 확인하고 결과 반환
        return existingStore != null;*/
        count++;
        if(count == 3){ 
            System.out.println("예외 발생");
            return true;
        }

        return false;
    }



    //구별로 가게 리스트 select
   /* @Override
    public ArrayList<Store> selectStoreList(String gugunNm) {
        return null;
    }*/


    public int insertBookmark(Bookmark bookmark){
        return storeDao.insertBookmark(bookmark);
    }

    @Override
    public int deleteBookmark(Bookmark bookmark) {
        return storeDao.deleteBookmark(bookmark);
    }


}

