package com.example.demo.src.inquiry.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Inquiry {

    int inquiryIdx;
    int userIdx;
    String category;
    String description;
    String inquiryUrl;
    String answerStatus;
    String createdAt;
}
