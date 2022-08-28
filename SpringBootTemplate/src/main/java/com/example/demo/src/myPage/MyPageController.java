package com.example.demo.src.myPage;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.inquiry.model.GetMyInquiryRes;
import com.example.demo.src.myPage.model.MyPage;
import com.example.demo.src.myPage.model.MyPageFollowing;
import com.example.demo.src.myPage.model.MyPageZzim;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@RestController
@RequestMapping("/app/my-page")
public class MyPageController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final MyPageService myPageService;
    @Autowired
    private final MyPageProvider myPageProvider;
    @Autowired
    private final JwtService jwtService;

    public MyPageController(MyPageService myPageService, MyPageProvider myPageProvider, JwtService jwtService) {
        this.myPageService = myPageService;
        this.myPageProvider = myPageProvider;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/{userIdx}/{sellingStatus}")
    public BaseResponse<MyPage> getMyPage(@PathVariable("userIdx") int userIdx, @PathVariable("sellingStatus") String sellingStatus)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            MyPage myPage = myPageProvider.getMyPage(userIdx,sellingStatus);
            return new BaseResponse<>(myPage);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @GetMapping("/following/{userIdx}")
    public BaseResponse<List<MyPageFollowing>> getMyPageFollowing(@PathVariable("userIdx") int userIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            List<MyPageFollowing> getMyPageFollowing = myPageProvider.getMyPageFollowing(userIdx);
            return new BaseResponse<>(getMyPageFollowing);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/follower/{userIdx}")
    public BaseResponse<List<MyPageFollowing>> getMyPageFollower(@PathVariable("userIdx") int userIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            List<MyPageFollowing> getMyPageFollowing = myPageProvider.getMyPageFollower(userIdx);
            return new BaseResponse<>(getMyPageFollowing);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/zzim/{userIdx}")
    public BaseResponse<List<MyPageZzim>> getMyPageZzim(@PathVariable("userIdx") int userIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            List<MyPageZzim> getMyPageZzim = myPageProvider.getMyPageZzim(userIdx);
            return new BaseResponse<>(getMyPageZzim);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
