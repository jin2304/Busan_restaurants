package com.web.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.web.store.dto.BookmarkCheckDto;
import com.web.store.dto.CustomUserDetails;
import com.web.store.entity.Bookmark;
import com.web.store.entity.Store;
import com.web.store.service.Interface.StoreService;
import com.web.store.utils.PageInfo;
import com.web.store.utils.Pagination;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/store")
public class StoreController {

    private static final String serviceKey ="PAhgnbn0dv5x/G8sIvPjQXcSe3Vgcdn13kIrPK7yfjzGK0EtQTiVRyBtp00CUwFFH76BCLdz2rCyJKJXyFqZEg==";

    private RestTemplate restTemplate;
    private StoreService storeService;

    @Autowired
    public StoreController(RestTemplate restTemplate, StoreService storeService) {
        this.restTemplate = restTemplate;
        this.storeService = storeService;
    }





    @ResponseBody
    @RequestMapping(value = "/storeSave", produces = "application/json; charset=UTF-8")
    //@GetMapping("/storeList")
    public ResponseEntity<String> store(Model model) throws JsonProcessingException {
    //public String store(Model model) throws JsonProcessingException {

        String url = "http://apis.data.go.kr/6260000/FoodService/getFoodKr";
        url += "?serviceKey=" + serviceKey;
        url += "&numOfRows=" + 20;
        url += "&resultType=json";
        url += "&pageNo=" + 1;

       ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

       String responseBody = responseEntity.getBody();
       //System.out.println("responseBody = " + responseBody);

//       String responseHeaders = responseEntity.getHeaders().toString();
//       System.out.println("responseHeaders = " + responseHeaders);

        

        //JSON 응답을 직접 파싱
        JSONParser parser = new JSONParser();
        List<Store> stores = new ArrayList<>();

        try {
            JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);
            JSONObject getFoodKr = (JSONObject) jsonResponse.get("getFoodKr");
            JSONArray itemList = (JSONArray) getFoodKr.get("item");
            //System.out.println("itemList = " + itemList);


            // 추출한 데이터를 처리하고 데이터베이스에 저장
            for (Object item : itemList) {
                JSONObject foodItem = (JSONObject) item;


                    // JSON 객체를 Store 객체로 변환
                    Store store = new Store(
                            Integer.valueOf(foodItem.get("UC_SEQ").toString())
                            ,foodItem.get("MAIN_TITLE").toString()
                            ,foodItem.get("GUGUN_NM").toString()
                            ,foodItem.get("ADDR1").toString()
                            ,foodItem.get("CNTCT_TEL").toString()
                            ,foodItem.get("USAGE_DAY_WEEK_AND_TIME").toString()
                            ,foodItem.get("RPRSNTV_MENU").toString()
                            ,foodItem.get("MAIN_IMG_NORMAL").toString()
                            ,foodItem.get("MAIN_IMG_THUMB").toString()
                            ,foodItem.get("ITEMCNTNTS").toString()
                            ,Double.parseDouble(foodItem.get("LAT").toString())
                            ,Double.parseDouble(foodItem.get("LNG").toString())
                    );

                    stores.add(store);
                    //System.out.println("store = " + store);
                    System.out.println();


                    int result =  storeService.insertStore(store);
                    if(result>0) {
                        System.out.println("성공");
                    } else {
                        System.out.println("실패");
                    }


                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

  /*      model.addAttribute("stores", stores);
        return "store/storeList";*/
            return ResponseEntity.ok()
                    .body(responseBody);
    }



    /** 맛집 목록 **/
    @GetMapping("/storeList")
    public String storeList(@RequestParam(value="cPage", defaultValue = "1")int currentPage, Model model){

        PageInfo pageInfo = Pagination.getPageInfo(storeService.selectListCount(), currentPage, 10, 16);
        System.out.println("storeService.selectListCount() = " + storeService.selectListCount());

        System.out.println("pageInfo.getCurrentPage() = " + pageInfo.getCurrentPage());


        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("stores",storeService.selectStoreList(pageInfo));

        return "store/storeList";
    }


    /** 맛집 상세 정보 **/
    @GetMapping("/storeDetail")
    public String storeDetail(@RequestParam("ucSeq")int ucSeq, Model model){
        System.out.println("호출");

        Store store = storeService.selectStoreDetail(ucSeq);

        if(store!=null){
            System.out.println("성공");
            model.addAttribute("store", store);
        }

        return "store/storeDetail";
    }



    @GetMapping("/map")
    public String testView(Model model){


        return "store/map";
    }


    /**
     * 맛집 북마크 확인
     **/
    @ResponseBody
    @GetMapping("/bookmark/check/{ucSeq}")
    public ResponseEntity<Integer> bookmarkCheck(@PathVariable final int ucSeq){

        // -> AOP 처리?
        // 현재 사용자의 Authentication 객체 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

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
    @ResponseBody
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
    //@ResponseBody
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
    @GetMapping(value = "/bookmark/delete/{bookmarkId}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Integer> removeBookmark(@PathVariable final int bookmarkId) {

        // 사용자의 ID 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        int userId = userDetails.getUserId();

        Bookmark bookmark = new Bookmark(userId, bookmarkId);
        int result = storeService.deleteBookmark(bookmark);


        return ResponseEntity.ok(result);
    }
    

}
