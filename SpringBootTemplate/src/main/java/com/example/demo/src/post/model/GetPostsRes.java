package com.example.demo.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostsRes {
    private String zzim; // 관심(찜)이 있는 상태인지 확인
    private String postImg; // 게시글의 사진
    private int price; // 가격
    private String postTitle; // 게시글 제목
    private String userRegion; // 게시자의 지역
    private String postingTime; // 게시 시간 (ex: 3일전)
    private String payStatus; // 번개 페이 가능여부
    private int interestNum; // 관심수
}
