package com.example.demo.src.myPage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class MyPageZzim {
    private int postIdx;
    private String postImg_url;
    private int price;
    private String userName;
    private String createdAt;
}
