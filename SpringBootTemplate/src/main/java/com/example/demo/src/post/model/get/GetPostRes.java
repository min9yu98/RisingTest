package com.example.demo.src.post.model.get;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostRes {
    private long postIdx;
    private int price;
    private boolean payStatus;
    private String postTitle;
    private String tradeRegion; // 유저의 사는 곳
    private String postingTime;
    private long viewNum; // 조횟수
    private long likeNum; // 관심 수, 찜한 수
    private long chatNum; // 채팅수
    private String prodStatus;
    private long quantity;
    private String deliveryFee;
    private String exchange;
    private String postContent;
    private String sellingStatus;
    private boolean zzimStatus;
}
// 사진과 해시태그는 따로 api (2 개)
