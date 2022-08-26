package com.example.demo.src.myPage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostFollow{
    private int postIdx;
    private String postImg_url;
    private int price;
}
