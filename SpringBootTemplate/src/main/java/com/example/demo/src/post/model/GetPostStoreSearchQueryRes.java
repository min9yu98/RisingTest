package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostStoreSearchQueryRes {
    private String profileImg_url;
    private String storeName;
    private long followerNum;
    private long postNum;
}
