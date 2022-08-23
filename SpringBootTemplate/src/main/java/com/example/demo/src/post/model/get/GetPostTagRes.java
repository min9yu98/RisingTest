package com.example.demo.src.post.model.get;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostTagRes {
    private long postIdx;
    private String hashTagName;
}
