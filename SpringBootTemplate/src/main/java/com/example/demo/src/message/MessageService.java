package com.example.demo.src.message;

import com.example.demo.config.BaseException;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

import static com.example.demo.config.BaseResponseStatus.POST_AUTHENTICATION_FAILURE;
import static com.example.demo.config.BaseResponseStatus.POST_AUTHENTICATION_FAILURE_PHONENUM;
import static com.example.demo.src.message.MessageController.numStr;

@Service
public class MessageService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    public void certifiedPhoneNumber(String phoneNumber, String cerNum) {
        String api_key = "NCSUOMQEX8RIT8YP";
        String api_secret = "0PX14PVQ0JNKOIJ64MTUSDDCNACHTIY2";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "01022703842");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "핫띵크 휴대폰인증 테스트 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }

    public boolean checkAuth(String pwd, String authpwd){ // pwd : 사용자의 인증번호, authpwd : 정답 인증번호
        if (!Objects.equals(pwd, authpwd)){
            return false;
        }
        return true;
    }


    public String sendSMS(PostAuthReq postAuthReq) throws BaseException {
        try {
            String num = "";
            Random rand = new Random();
            for (int i = 0; i < 4; i++){
                String ran = Integer.toString(rand.nextInt(10));
                num += ran;
            }
            certifiedPhoneNumber(postAuthReq.getPhoneNumber(), num);
            numStr = num;
            return numStr;
        } catch (Exception exception){
            throw new BaseException(POST_AUTHENTICATION_FAILURE);
        }

    }
}