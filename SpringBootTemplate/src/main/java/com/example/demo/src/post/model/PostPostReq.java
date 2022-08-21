package com.example.demo.src.post.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPostReq {
    private String postImg_url;
    private String postTItle;
    private int category_idx;
    private int price;
    private String hashTagName;
    private String userRegion; // 유저의 사는 곳
    private String postingTime;
    private int viewNum; // 조횟수
    private int interestNum; // 관심 수, 찜한 수
    private int chatNum; // 채팅수

}