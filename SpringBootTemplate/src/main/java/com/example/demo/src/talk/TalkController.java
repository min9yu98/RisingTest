package com.example.demo.src.talk;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.talk.model.GetSpecificTalkList;
import com.example.demo.src.talk.model.GetTalkList;
import com.example.demo.src.talk.model.MessageReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/talk")
public class TalkController {

    @Autowired
    private final TalkProvider talkProvider;
    @Autowired
    private final TalkService talkService;
    @Autowired
    private final JwtService jwtService;

    public TalkController(TalkProvider talkProvider, TalkService talkService, JwtService jwtService) {
        this.talkProvider = talkProvider;
        this.talkService = talkService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("/create/{userIdx}/{postUserIdx}")
    public BaseResponse<String> createFollow(@RequestBody MessageReq messageReq,
                                             @PathVariable("userIdx") int userIdx,
                                             @PathVariable("postUserIdx") int postUserIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            talkService.createTalk(messageReq,userIdx,postUserIdx);
            return new BaseResponse<>("메세지 전송완료");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetTalkList>> getTalkList(@PathVariable("userIdx") int userIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            List<GetTalkList> getTalkList = talkProvider.getTalkList(userIdx);
            return  new BaseResponse<>(getTalkList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/specific/{talkRoomIdx}")
    public BaseResponse<List<GetSpecificTalkList>> getSpecificTalkList(@PathVariable("talkRoomIdx") int talkRoomIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            List<GetSpecificTalkList> getSpecificTalkList = talkProvider.getSpecificTalkList(talkRoomIdx);
            return  new BaseResponse<>(getSpecificTalkList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }




}
