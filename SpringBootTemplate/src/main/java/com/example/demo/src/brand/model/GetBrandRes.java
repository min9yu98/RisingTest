package com.example.demo.src.brand.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetBrandRes {
    private long brandIdx;
    private String brandImg_url;
    private String brandName;
    private String brandEngName;
    private long postNum;
}
