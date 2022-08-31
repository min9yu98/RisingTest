package com.example.demo.src.keyword.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KeyWordAlarmRes {
    String keyWord;
    String postTitle;
    String postImg_url;
    String createdAt;


    public KeyWordAlarmRes(String postTitle, String postImg_url, String createdAt) {
        this.postTitle = postTitle;
        this.postImg_url = postImg_url;
        this.createdAt = createdAt;
    }
}
