package com.web.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class BookmarkDto {

    //int store_ucSeq;
    int user_userId;
    private int ucSeq;
    private String mainTitle;
    private String gugunNm;
    private String addr1;
    private String cntctTel;
    private String usageDayWeekAndTime;
    private String reprsntvMenu;
    private String mainImgNormal;
    private String mainImgThumb;
    private String itemCntnts;
    private double lat;
    private double lng;
}
