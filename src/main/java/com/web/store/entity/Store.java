package com.web.store.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Store {
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