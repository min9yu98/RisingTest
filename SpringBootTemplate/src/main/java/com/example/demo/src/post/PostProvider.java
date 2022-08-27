package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.get.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class PostProvider {
    private final PostDao postDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PostProvider(PostDao postDao) {
        this.postDao = postDao;
    }

    public List<GetPostsRes> getPosts(long userIdx, long pageNum) throws BaseException {
        try {
            PostGeneric<GetPostsRes> postGeneric = new PostGeneric<>();
            postGeneric.setPostsRes(postDao.getPosts(userIdx));
            return postGeneric.printResult(pageNum);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPostRes getPost(long userIdx, long postIdx) throws BaseException{
        try {
            GetPostRes getPostRes = postDao.getPost(userIdx, postIdx);
            return getPostRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetPostImgRes> getImg(long userIdx, long postIdx) throws BaseException{
        try {
            List<GetPostImgRes> getPostImgRes = postDao.getImg(userIdx, postIdx);
            return getPostImgRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetPostTagRes> getTag(long userIdx, long postIdx) throws BaseException {
        try {
            List<GetPostTagRes> getPostTagRes = postDao.getTag(userIdx, postIdx);
            return getPostTagRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 게시글 검색
    public List<GetPostSearchRes> getQueryPosts(String query, long userIdx, long pageNum) throws BaseException{
        try {
            PostGeneric<GetPostSearchRes> postGeneric = new PostGeneric<>();
            postGeneric.setPostsRes(postDao.getQueryPosts(query, userIdx));
            return postGeneric.printResult(pageNum);
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상점 정보
    public GetPostStoreRes getQueryStore(long userIdx) throws BaseException{
        try{
            GetPostStoreRes getPostStoreRes = postDao.getQueryStore(userIdx);
            return getPostStoreRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상점의 게시글들
    public List<GetPostStorePostRes> getQueryStorePost(long userIdx, long storeUserIdx, long pageNum) throws BaseException{
        try {
            PostGeneric<GetPostStorePostRes> postGeneric = new PostGeneric<>();
            postGeneric.setPostsRes(postDao.getQueryStorePost(userIdx, storeUserIdx));
            return postGeneric.printResult(pageNum);
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상점 검색시 상점들 이름 목록
    public List<GetPostStoreSearchQueryRes> getQueryStoreList(String query) throws BaseException{
        try {
            List<GetPostStoreSearchQueryRes> getPostStoreSearchRes = postDao.getQueryStoreList(query);
            return getPostStoreSearchRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 상품 검색시 상품들 이름 목록
    public List<GetPostSearchQueryRes> getQueryPostList(String query) throws BaseException{
        try {
            List<GetPostSearchQueryRes> getPostSearchQueryRes = postDao.getQueryPostList(query);
            return getPostSearchQueryRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 카테고리 클릭시 나오는 게시글들 조회
    public List<GetCategoryPostRes> getCategoryPost(long userIdx, int idx, long pageNum) throws BaseException{
        try {
            PostGeneric<GetCategoryPostRes> postGeneric = new PostGeneric<>();
            postGeneric.setPostsRes(postDao.getCategoryPost(userIdx, idx));
            return postGeneric.printResult(pageNum);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
