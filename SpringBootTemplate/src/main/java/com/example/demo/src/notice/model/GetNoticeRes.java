package com.example.demo.src.notice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetNoticeRes {
    private String noticeTitle;
    private String postingTime;
    private String noticeContent;
    private String noticeImg_url;
}
