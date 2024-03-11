package com.web.store.dao.mybatis;

import com.web.store.dao.StoreDao;
import com.web.store.dto.BookmarkCheckDto;
import com.web.store.dto.BookmarkDto;
import com.web.store.entity.Bookmark;
import com.web.store.entity.Store;
import com.web.store.utils.PageInfo;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.io.IOException;
import java.util.List;

@Repository
public class MyBatisStoreDao implements StoreDao {

    private StoreDao mapper;

    @Autowired
    public MyBatisStoreDao(SqlSession sqlSession) {
        mapper = sqlSession.getMapper(StoreDao.class);
        //세션을 통해 mapper 컨테이너에서 mapper 객체를 꺼내 씀
    }



    @Override
 /*   public int insertStore(Store store) {
        return mapper.insertStore(store);
    }*/

    public int insertStore(Store store) {
        try {
            return mapper.insertStore(store);
        } catch (Exception e) {
           // throw new DataAccessException("Failed to insert store: " + e.getMessage(), e);
            System.err.println("Failed to insert store: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int selectListCount() {
        return mapper.selectListCount();
    }


    @Override
    public List<Store> selectStoreList(PageInfo pageInfo, RowBounds rowBounds) {
        //System.out.println("pageInfo = " + pageInfo);
        List<Store> stores = mapper.selectStoreList(pageInfo, rowBounds);

   /*     for (Store store : stores) {
            System.out.println(store); // Store 객체의 toString() 메서드를 이용한 출력
            System.out.println();
        }*/



        return stores;
    }

    @Override
    public Store selectStoreDetail(int unSeq) {
        return mapper.selectStoreDetail(unSeq);
    }


    @Override
    public int insertBookmark(Bookmark bookmark) {
        return mapper.insertBookmark(bookmark);
    }

    @Override
    public int deleteBookmark(Bookmark bookmark) {
        return  mapper.deleteBookmark(bookmark);
    }

    @Override
    public List<BookmarkDto> selectBookmarkStore(int userId) {
        return mapper.selectBookmarkStore(userId);
    }

    @Override
    public List<Bookmark> selectBookmarkList(int userId) {
        return mapper.selectBookmarkList(userId);
    }



    @Override
    public int checkBookmark(BookmarkCheckDto bookmarkCheckDto) {
        return mapper.checkBookmark(bookmarkCheckDto);
    }


    @Override
    public int increaseCount(int ucSeq) {
        return 0;
    }

    @Override
    public Store selectStore(int ucSeq) {
        return null;
    }

    @Override
    public int deleteStore(int ucSeq) {
        return 0;
    }

    @Override
    public int updateBoard(Store store) {
        return 0;
    }


}
