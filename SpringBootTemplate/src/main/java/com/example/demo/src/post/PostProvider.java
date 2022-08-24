package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.get.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<GetPostsRes> getPosts(long userIdx) throws BaseException {
        try {
            List<GetPostsRes> getPostRes = postDao.getPosts(userIdx);
            return getPostRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetPostSearchRes> getQueryPosts(String query) throws BaseException{
        try {
            List<GetPostSearchRes> getPostsRes = postDao.getQueryPosts(query);
            return getPostsRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPostStoreRes getQueryStore(long userIdx) throws BaseException{
        try{
            GetPostStoreRes getPostStoreRes = postDao.getQueryStore(userIdx);
            return getPostStoreRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetPostStorePostRes> getQueryStorePost(long userIdx) throws BaseException{
        try {
            List<GetPostStorePostRes> getPostStorePostRes = postDao.getQueryStorePost(userIdx);
            return getPostStorePostRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetPostStoreSearchQueryRes> getQueryStoreList(String query) throws BaseException{
        try {
            List<GetPostStoreSearchQueryRes> getPostStoreSearchRes = postDao.getQueryStoreList(query);
            return getPostStoreSearchRes;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetPostSearchQueryRes> getQueryPostList(String query) throws BaseException{
        try {
            List<GetPostSearchQueryRes> getPostSearchQueryRes = postDao.getQueryPostList(query);
            return getPostSearchQueryRes;
        } catch(Exception exception){
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
}
