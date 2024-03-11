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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.sql.SQLException;
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

/*    @Override
    @Transactional(rollbackFor = {DuplicateKeyException.class, RuntimeException.class, SQLException.class})
    public int insertStore(Store store) {
        try {
            return storeDao.insertStore(store);
        } catch (DuplicateKeyException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert store", e);
        }
    }*/

    //DB 삽입
/*    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public int insertStore(Store store){
        
        
        //중복 확인
        if (isDuplicateStore(store)) {
            throw new RuntimeException("중복된 가게입니다.");
            //throw new IllegalStateException("이미 존재하는 가게입니다.");
        }
        return storeDao.insertStore(store);

*//*        try {
            return storeDao.insertStore(store);
        } catch (DuplicateKeyException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to insert store", e);
        }*//*


    }*/


    @Override
    @Transactional(rollbackFor = {DuplicateKeyException.class, RuntimeException.class, SQLException.class})
    //@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int insertStore(Store store) {
        try {
            return storeDao.insertStore(store);
        }   catch (DuplicateKeyException e) {
            // 중복 예외가 발생하면 롤백을 수행하고 해당 예외를 다시 던짐
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw e;
        } catch (Exception e) {
            // 중복된 값이 발생하면 롤백 수행
            System.out.println("e = " + e);
            throw new RuntimeException("Failed to insert store: " + e.getMessage(), e);
        }
    }

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

