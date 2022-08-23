package com.example.demo.src.post.model.patch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchDeletePostReq {
    private long userIdx;
    private long postIdx;
}
