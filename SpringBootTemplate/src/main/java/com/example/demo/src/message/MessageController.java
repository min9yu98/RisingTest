package com.example.demo.src.message;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.PostProvider;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Random;

import static com.example.demo.config.BaseResponseStatus.POST_AUTHENTICATION_FAILURE;
import static com.example.demo.config.BaseResponseStatus.POST_AUTHENTICATION_FAILURE_PHONENUM;

@RestController
@RequestMapping("/app/auth")
public class MessageController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final MessageService messageService;


    public static String numStr;
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ResponseBody
    @PostMapping("/check/sendSMS")
    public BaseResponse<String> sendSMS(@RequestBody PostAuthReq postAuthReq){
        try{
            if (postAuthReq.getPhoneNumber() == null){
                return new BaseResponse<>(POST_AUTHENTICATION_FAILURE_PHONENUM);
            }
            numStr = messageService.sendSMS(postAuthReq);
            return new BaseResponse<>(numStr);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @PostMapping("/check")
    public BaseResponse<String> check(@RequestBody PostCheckReq postCheckReq) {
        String resultMessage = "인증 실패하였습니다.";
        if (messageService.checkAuth(postCheckReq.getNumStr(), numStr)){
            resultMessage = "인증되었습니다.";
        }
        return new BaseResponse<>(resultMessage);
    }
}