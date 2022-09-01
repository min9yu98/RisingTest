package com.example.demo.src.inquiry.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateInquiryReq {
    private String category;
    private String description;
    private List<String> inquiry_Url;
}
