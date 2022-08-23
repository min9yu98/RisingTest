package com.example.demo.src.post.model.get;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private long postIdx;
    private String postImg_url; // 게시글의 사진
    private int price; // 가격
    private String postTitle; // 게시글 제목
    private String tradeRegion; // 게시자의 지역
    private String postingTime; // 게시 시간 (ex: 3일전)
    private boolean payStatus; // 번개 페이 가능여부
    private long likeNum; // 관심수
    private String sellingStatus; // 판매중 또는 판매완료
}
