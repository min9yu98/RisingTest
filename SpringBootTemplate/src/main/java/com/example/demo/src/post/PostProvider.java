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

    public boolean checkPage(long pageNum, long total){
        if (total >= pageNum){
            return true;
        }
        return false;
    }

//    public List<Object> printResult(Object object, long size, long page, long pageNum){
//        List<Object> arr = new ArrayList<>();
//
//        if (checkPage(pageNum, page)) {
//            if (page == pageNum) {
//                for (long i = (pageNum - 1) % 20; i < 20 * (pageNum - 1) + (size % 20); i++) {
//                    arr.add(object.get((int) i));
//                }
//            } else {
//                for (long i = (pageNum - 1) * 20; i < 20 * pageNum - 1; i++) {
//                    arr.add(object.get((int) i));
//                }
//            }
//        }
//        return arr;
//    }

    public List<GetPostsRes> getPosts(long userIdx, long pageNum) throws BaseException {
        try {
            List<GetPostsRes> getPostsRes = postDao.getPosts(userIdx);
            long size = getPostsRes.size();
            long page = size / 20;
            page += (size % 20 != 0) ? 1 : 0;
            List<GetPostsRes> arrGetPostsRes = new ArrayList<>();
            if (checkPage(pageNum, page)){

                if (page == pageNum){
                    for (long i = (pageNum - 1) * 20; i < 20 * (pageNum - 1) + (size % 20); i++){
                        arrGetPostsRes.add(getPostsRes.get((int)i));
                    }
                } else {
                    for (long i = (pageNum - 1) * 20; i < 20 * pageNum - 1; i++){
                        arrGetPostsRes.add(getPostsRes.get((int)i));
                    }
                }
            }
            return arrGetPostsRes;
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
    public List<GetPostSearchRes> getQueryPosts(String query, long userIdx, long pageNum) throws BaseException{
        try {
            List<GetPostSearchRes> getPostsRes = postDao.getQueryPosts(query, userIdx);
            long size = getPostsRes.size();
            long page = size / 20;
            page += (size % 20 != 0) ? 1 : 0;
            List<GetPostSearchRes> arrGetPostSearchRes = new ArrayList<>();

            if (checkPage(pageNum, page)) {
                if (page == pageNum) {
                    for (long i = (pageNum - 1) % 20; i < 20 * (pageNum - 1) + (size % 20); i++) {
                        arrGetPostSearchRes.add(getPostsRes.get((int) i));
                    }
                } else {
                    for (long i = (pageNum - 1) * 20; i < 20 * pageNum - 1; i++) {
                        arrGetPostSearchRes.add(getPostsRes.get((int) i));
                    }
                }
            }
            return arrGetPostSearchRes;
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

    public List<GetPostStorePostRes> getQueryStorePost(long userIdx, long storeUserIdx, long pageNum) throws BaseException{
        try {
            List<GetPostStorePostRes> getPostStorePostRes = postDao.getQueryStorePost(userIdx, storeUserIdx);

            long size = getPostStorePostRes.size();
            long page = size / 20;
            page += (size % 20 != 0) ? 1 : 0;
            List<GetPostStorePostRes> arrStorePostRes = new ArrayList<>();

            if (checkPage(pageNum, page)){

                if (page == pageNum){
                    for (long i = (pageNum - 1) * 20; i < 20 * (pageNum - 1) + (size % 20); i++){
                        arrStorePostRes.add(getPostStorePostRes.get((int)i));
                    }
                } else {
                    for (long i = (pageNum - 1) * 20; i < 20 * pageNum - 1; i++){
                        arrStorePostRes.add(getPostStorePostRes.get((int)i));
                    }
                }
            }
            return arrStorePostRes;
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

    public List<GetCategoryPostRes> getCategoryPost(long userIdx, int idx, long pageNum) throws BaseException{
        try {
            List<GetCategoryPostRes> getCategoryPostRes = postDao.getCategoryPost(userIdx, idx);

            long size = getCategoryPostRes.size();
            long page = size / 20;
            page += (size % 20 != 0) ? 1 : 0;
            List<GetCategoryPostRes> arrCategoryPostRes = new ArrayList<>();

            if (checkPage(pageNum, page)){

                if (page == pageNum){
                    for (long i = (pageNum - 1) * 20; i < 20 * (pageNum - 1) + (size % 20); i++){
                        arrCategoryPostRes.add(getCategoryPostRes.get((int)i));
                    }
                } else {
                    for (long i = (pageNum - 1) * 20; i < 20 * pageNum - 1; i++){
                        arrCategoryPostRes.add(getCategoryPostRes.get((int)i));
                    }
                }
            }
            return arrCategoryPostRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
