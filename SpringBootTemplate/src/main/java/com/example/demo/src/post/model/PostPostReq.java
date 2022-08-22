package com.example.demo.src.post.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPostReq {
    private List<String> postImg_url;
    private String tradeRegion;
    private String postTitle;
    private int categoryIdx;
    private List<String> hashTagName;
    private int price;
    private String deliveryFee;
    private long quantity;
    private String prodStatus;
    private String exchange;
    private String postContent;
    private String payStatus;
}
