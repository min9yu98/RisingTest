package com.example.demo.src.myPage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostMyPage {
    private String postImg_url;
    private int postIdx;
    private String postTitle;
    private int price;
    private String postUserName;
}
