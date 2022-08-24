package com.example.demo.src.post.model.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostStoreRes {
    private String storeName;
    private String userRegion;
    private String description;
}
