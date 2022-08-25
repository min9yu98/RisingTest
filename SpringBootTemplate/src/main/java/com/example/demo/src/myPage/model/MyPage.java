package com.example.demo.src.myPage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    private List<PostMyPage> postMyPages;

    public MyPage(int zzim_num) {
        this.zzim_num = zzim_num;
    }

    public MyPage(String userName, String profileImg_url) {
        this.userName = userName;
        this.profileImg_url = profileImg_url;
    }
}
