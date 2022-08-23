package com.example.demo.src.post.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostRes {
    private int price;
    private String prodStatus;
    private String postTItle;
    private String tradeRegion; // 유저의 사는 곳
    private String postingTime;
    private long viewNum; // 조횟수
    private long interestNum; // 관심 수, 찜한 수
    private long chatNum; // 채팅수
    private String quantity;
    private String deliveryFee;
    private String exchange;
    private String postContent;
}
// 사진과 해시태그는 따로 api (2 개)
