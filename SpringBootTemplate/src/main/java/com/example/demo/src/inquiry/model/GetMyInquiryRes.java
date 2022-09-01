package com.example.demo.src.inquiry.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetMyInquiryRes {

    private int inquiryIdx;
    private String category;
    private String createdAt;
    private String answerStatus;
}
