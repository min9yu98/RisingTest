package com.example.demo.src.message;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.post.PostProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/app/auth")
public class MessageController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    public static String numStr = "";

    @ResponseBody
    @PostMapping("/check/sendSMS")
    public String sendSMS(@RequestBody PostAuthReq postAuthReq){
        Random rand = new Random();
        for (int i = 0; i < 4; i++){
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }
        messageService.certifiedPhoneNumber(postAuthReq.getPhoneNumber(), numStr);
        return numStr;
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
