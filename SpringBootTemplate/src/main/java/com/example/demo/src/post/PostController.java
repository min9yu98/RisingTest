package com.example.demo.src.post;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.post.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/posts")
public class PostController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final PostProvider postProvider;
    @Autowired
    private final PostService postService;


    public PostController(PostProvider postProvider, PostService postService) {
        this.postProvider = postProvider;
        this.postService = postService;
    }

    @ResponseBody
    @GetMapping("/{userIdx}/posts") // 로그인 때문에
    public BaseResponse<List<GetPostsRes>> getPosts(@PathVariable("userIdx") long userIdx) {
        try {
            List<GetPostsRes> getPostingsRes = postProvider.getPosts(userIdx);
            return new BaseResponse<>(getPostingsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{userIdx}/posts/{postIdx}")
    public BaseResponse<GetPostRes> getPost(@PathVariable("userIdx") long userIdx, @PathVariable("postIdx") long postIdx){
        try{
            GetPostRes getPostRes = postProvider.getPost(userIdx);
            return new BaseResponse<>(getPostRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 상품 검색시 상품들
    @GetMapping("/search-prod")
    public BaseResponse<List<GetPostSearchRes>> getQueryPosts(@RequestParam(name="query") String query){
        try {
            List<GetPostSearchRes> getPostsRes = postProvider.getQueryPosts(query);
            return new BaseResponse<>(getPostsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 상품 검색시 상품들 이름 목록
    @GetMapping("/search-prod-list")
    public BaseResponse<List<GetPostSearchQueryRes>> getQueryPostList(@RequestParam(name="query") String query){
        try {
            List<GetPostSearchQueryRes> getPostSearchQueryRes = postProvider.getQueryPostList(query);
            return new BaseResponse<>(getPostSearchQueryRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 상점 검색시 상점들 이름 목록
    @GetMapping("/search-store-list")
    public BaseResponse<List<GetPostStoreSearchQueryRes>> getQueryStoreList(@RequestParam(name="query") String query){
        try {
            List<GetPostStoreSearchQueryRes> getStorePostsRes = postProvider.getQueryStoreList(query);
            return new BaseResponse<>(getStorePostsRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/{userIdx}/new")
    public BaseResponse<PostPostRes> registerPost(@RequestBody PostPostReq postPostReq, @PathVariable("userIdx") int userIdx)
    {
        if (postPostReq.getPostImg_url() == null){
            return new BaseResponse<>(POST_POST_EMPTY_POST_IMG);
        } else if (postPostReq.getPostImg_url().size() > 12){
            return new BaseResponse<>(POST_POST_OVER_POST_IMG);
        }
        if (postPostReq.getPostTitle() == null){
            return new BaseResponse<>(POST_POST_INVALID_TITLE);
        }
        if (postPostReq.getCategoryIdx() == 0){
            return new BaseResponse<>(POST_POST_EMPTY_CATEGORY);
        }
        if (postPostReq.getHashTagName() == null){
            return new BaseResponse<>(POST_POST_EMPTY_TAG);
        }
        if (postPostReq.getPrice() == 0) {
            return new BaseResponse<>(POST_POST_EMPTY_PRICE);
        }


        try {
            PostPostRes postPostRes = postService.registerPost(postPostReq, userIdx);
            return new BaseResponse<>(postPostRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}