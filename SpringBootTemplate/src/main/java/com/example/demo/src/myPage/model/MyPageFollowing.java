package com.example.demo.src.myPage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MyPageFollowing {

    private int postUserIdx;
    private String postUserName;
    private String profileImg_url;
    private int postNum;
    private int followerNum;
    private List<PostFollow> postFollows;

    public MyPageFollowing(int postUserIdx, String postUserName, String profileImg_url) {
        this.postUserIdx = postUserIdx;
        this.postUserName = postUserName;
        this.profileImg_url = profileImg_url;
    }
}
