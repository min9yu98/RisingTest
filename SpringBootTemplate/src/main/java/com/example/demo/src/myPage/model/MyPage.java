package com.example.demo.src.myPage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyPage {
    private String userName;
    private String profileImg_url;
    private int zzim_num;
    private int review_num;
    private int follower_num;
    private int following_num;
}
