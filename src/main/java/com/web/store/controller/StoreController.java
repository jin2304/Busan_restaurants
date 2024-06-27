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
import org.springframework.dao.DataAccessException;
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
    public ResponseEntity<String> storeSave(Model model) throws JsonProcessingException {
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
                }
                    int result = storeService.insertStores(stores); //DB에 가게 저장
                    System.out.println("result: " + result);
                    return ResponseEntity.ok().body(responseBody);

                } catch (ParseException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to parse response.");
                } catch (DataAccessException e) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate store found!");
                    //return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate store found: " + e.getMessage());
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
                }

  /*      model.addAttribute("stores", stores);
            return "store/storeList";*/

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



}
