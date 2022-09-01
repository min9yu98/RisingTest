package com.example.demo.src.inquiry.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InquirySpecificRes {
    String inquiryCreatedAt;
    String inquiryDescription;
    String category;
    String answerDescription;
    String answerCreatedAt;
    String answerStatus;

    public InquirySpecificRes(String inquiryCreatedAt, String inquiryDescription, String category,String answerStatus) {
        this.inquiryCreatedAt = inquiryCreatedAt;
        this.inquiryDescription = inquiryDescription;
        this.category = category;
        this.answerStatus = answerStatus;
    }
}
