package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.PostPostReq;
import com.example.demo.src.post.model.PostPostRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class PostService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final PostDao postDao;

    public PostService(PostDao postDao, PostProvider postProvider) {
        this.postDao = postDao;
        this.postProvider = postProvider;
    }

    @Autowired
    private final PostProvider postProvider;
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
}
