package com.example.demo.src.Follow;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/follow")
public class FollowController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final FollowService followService;
    @Autowired
    private final JwtService jwtService;

    public FollowController(FollowService followService, JwtService jwtService) {
        this.followService = followService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("/create/{userIdx}/{postUserIdx}")
    public BaseResponse<String> createFollow(@PathVariable("userIdx") int userIdx,
                                           @PathVariable("postUserIdx") int postUserIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            followService.createFollow(userIdx,postUserIdx);
            return new BaseResponse<>("알람이 등록 되었습니다");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/delete/{userIdx}/{postUserIdx}")
    public BaseResponse<String> deleteFollow(@PathVariable("userIdx") int userIdx,
                                           @PathVariable("postUserIdx") int postUserIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            followService.deleteFollow(userIdx,postUserIdx);
            return new BaseResponse<>("알람이 취소 되었습니다");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
