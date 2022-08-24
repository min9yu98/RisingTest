package com.example.demo.src.post.model.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCategoryPostRes {
    private long postIdx;
    private String postImg_url;
    private int price;
    private String postTitle;
    private boolean payStatus;
}
