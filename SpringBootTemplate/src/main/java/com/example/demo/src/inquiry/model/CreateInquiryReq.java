package com.example.demo.src.inquiry.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateInquiryReq {
    String category;
    String description;
    String inquiryUrl;
}
