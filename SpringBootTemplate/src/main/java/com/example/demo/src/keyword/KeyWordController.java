package com.example.demo.src.keyword;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.inquiry.model.CreateAnswerReq;
import com.example.demo.src.keyword.model.KeyWordAlarmRes;
import com.example.demo.src.keyword.model.KeyWordCreateReq;
import com.example.demo.src.myPage.model.MyPageZzim;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/keyword")
public class KeyWordController {
    @Autowired
    private final KeyWordService keyWordService;
    @Autowired
    private final KeyWordProvider keyWordProvider;
    @Autowired
    private final JwtService jwtService;

    public KeyWordController(KeyWordService keyWordService, KeyWordProvider keyWordProvider, JwtService jwtService) {
        this.keyWordService = keyWordService;
        this.keyWordProvider = keyWordProvider;
        this.jwtService = jwtService;
    }
    @ResponseBody
    @PostMapping("/create/{userIdx}")
    public BaseResponse<String> createKeyword(@RequestBody KeyWordCreateReq keyWordCreateReq,
                                              @PathVariable("userIdx") int userIdx)
    {
        try {
            //            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            keyWordService.createKeyWord(keyWordCreateReq, userIdx);
            // 중복 keyword 처리 할껀가 ?
            return new BaseResponse<>("키워드 등록이 완료되었습니다");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @PatchMapping("/delete/{userIdx}/{keyWord}")
    public BaseResponse<String> deleteKeyWord(@PathVariable("userIdx") int userIdx,
                                              @PathVariable("keyWord") String keyWord)
    {
        try {
            //            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            keyWordService.deleteKeyWord(userIdx,keyWord);
            return new BaseResponse<>("키워드 삭제가 완료되었습니다");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<KeyWordAlarmRes>> getKeyWordAlarm(@PathVariable("userIdx") int userIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            List<KeyWordAlarmRes> getMyKeyWordAlarm = keyWordProvider.getMyKeyWordAlarm(userIdx);
            return new BaseResponse<>(getMyKeyWordAlarm);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
