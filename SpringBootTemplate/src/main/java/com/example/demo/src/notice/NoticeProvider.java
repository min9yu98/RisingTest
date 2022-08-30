package com.example.demo.src.notice;

import com.example.demo.config.BaseException;
import com.example.demo.src.notice.model.GetNoticeRes;
import com.example.demo.src.post.PostDao;
import com.example.demo.src.post.PostGeneric;
import com.example.demo.src.post.model.get.GetPostsRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class NoticeProvider {
    private final NoticeDao noticeDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public NoticeProvider(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }
    public List<GetNoticeRes> getNotice(String category, long pageNum) throws BaseException {
        try {
            PostGeneric<GetNoticeRes> postGeneric = new PostGeneric<>();
            postGeneric.setPostsRes(noticeDao.getNotice(category, pageNum));
            return postGeneric.printResult(pageNum);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetNoticeRes> getNotices(long pageNum) throws BaseException{
        try {
            PostGeneric<GetNoticeRes> postGeneric = new PostGeneric<>();
            postGeneric.setPostsRes(noticeDao.getNotices(pageNum));
            return postGeneric.printResult(pageNum);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
