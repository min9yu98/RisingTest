package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.patch.PatchDeletePostReq;
import com.example.demo.src.post.model.patch.PatchEditPostReq;
import com.example.demo.src.post.model.patch.PatchEditPostRes;
import com.example.demo.src.post.model.post.PostPostReq;
import com.example.demo.src.post.model.post.PostPostRes;
import com.example.demo.src.post.model.post.PostReviewReq;
import com.example.demo.src.post.model.post.PostReviewRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class PostService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final PostDao postDao;
    @Autowired
    private final PostProvider postProvider;
    public PostService(PostDao postDao, PostProvider postProvider) {
        this.postDao = postDao;
        this.postProvider = postProvider;
    }

    public PostPostRes registerPost(PostPostReq postPostReq, long userIdx) throws BaseException {
        if (postPostReq.getPostTitle().length() < 2) {
            throw new BaseException(POST_POST_INVALID_TITLE);
        }
        if (postPostReq.getPrice() <= 0){
            throw new BaseException(POST_POST_NOT_ENOUGH_PRICE);
        }

        try {
            long postIdx = postDao.registerPost(postPostReq, userIdx);
            return new PostPostRes(postIdx);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostReviewRes registerReview(PostReviewReq postReviewReq, long postIdx, long userIdx) throws BaseException{
        try {
            long reviewIdx = postDao.registerReview(postReviewReq, postIdx, userIdx);
            return new PostReviewRes(reviewIdx);
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int deletePost(PatchDeletePostReq patchDeletePostReq) throws BaseException {
        try {
            int result = postDao.deletePost(patchDeletePostReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            return result;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int editPost(PatchEditPostReq patchEditPostReq, long userIdx, long postIdx) throws BaseException {
        try {
            if (patchEditPostReq.getPostTitle() != null && patchEditPostReq.getPostTitle().length() < 2) {
                throw new BaseException(POST_POST_INVALID_TITLE);
            }
            if (patchEditPostReq.getPrice() != 0 && patchEditPostReq.getPrice() <= 0){
                throw new BaseException(POST_POST_NOT_ENOUGH_PRICE);
            }
            int result = (int)postDao.editPost(patchEditPostReq, userIdx, postIdx);
            return result;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
