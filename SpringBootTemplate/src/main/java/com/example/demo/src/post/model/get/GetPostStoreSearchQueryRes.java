package com.example.demo.src.post.model.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostStoreSearchQueryRes {
    private long userIdx;
    private String profileImg_url;
    private String userName;
    private long followerNum;
    private long postNum;
}
