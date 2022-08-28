package com.example.demo.src.myPage;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.inquiry.model.GetMyInquiryRes;
import com.example.demo.src.myPage.model.MyPage;
import com.example.demo.src.myPage.model.MyPageFollowing;
import com.example.demo.src.myPage.model.MyPageZzim;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class MyPageProvider {

    private final MyPageDao myPageDao;
    private final JwtService jwtService;

    @Autowired

    public MyPageProvider(MyPageDao myPageDao, JwtService jwtService) {
        this.myPageDao = myPageDao;
        this.jwtService = jwtService;
    }

    @Transactional
    public MyPage getMyPage(int userIdx, String sellingStatus) throws BaseException {
        try {
            MyPage myPage =  myPageDao.getMyPageZzimNum(userIdx);
            myPage.setReview_num(myPageDao.getMyPagePostReviewNum(userIdx));
            myPage.setFollower_num(myPageDao.getMyPageFollowerNum(userIdx));
            myPage.setFollowing_num(myPageDao.getMyPageFollowingNum(userIdx));
            MyPage myPageNameImg = myPageDao.getMyPageNameImg(userIdx);
            myPage.setUserName(myPageNameImg.getUserName());
            myPage.setProfileImg_url(myPageNameImg.getProfileImg_url());
            myPage.setPostMyPages(myPageDao.getMyPostPages(userIdx, sellingStatus));
            return myPage;
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<MyPageFollowing> getMyPageFollowing(int userIdx) throws BaseException {
        try {
            List<MyPageFollowing> myPageFollowing = myPageDao.getMyPageFollowerNameImg(userIdx);
            for (MyPageFollowing pageFollowing : myPageFollowing) {
                pageFollowing.setFollowerNum(myPageDao.getMyPageFollowerNum(pageFollowing.getPostUserIdx()));
                pageFollowing.setPostNum(myPageDao.getMyPagePostNum(pageFollowing.getPostUserIdx()));
                pageFollowing.setPostFollows(myPageDao.getMyPagePosts(pageFollowing.getPostUserIdx()));
            }
            return myPageFollowing;
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }


    @Transactional
    public List<MyPageFollowing> getMyPageFollower(int userIdx) throws BaseException {
        try {
            List<MyPageFollowing> myPageFollowing = myPageDao.getMyPageFolloweeNameImg(userIdx);
            for (MyPageFollowing pageFollowing : myPageFollowing) {
                pageFollowing.setFollowerNum(myPageDao.getMyPageFollowerNum(pageFollowing.getPostUserIdx()));
                pageFollowing.setPostNum(myPageDao.getMyPagePostNum(pageFollowing.getPostUserIdx()));
                pageFollowing.setPostFollows(myPageDao.getMyPagePosts(pageFollowing.getPostUserIdx()));
            }
            return myPageFollowing;
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<MyPageZzim> getMyPageZzim(int userIdx) throws BaseException {
        try {
            List<MyPageZzim> myPageZzim = myPageDao.getMyPageZzim(userIdx);
            return myPageZzim;
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
