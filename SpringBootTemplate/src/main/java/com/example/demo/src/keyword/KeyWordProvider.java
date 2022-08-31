package com.example.demo.src.keyword;

import com.example.demo.config.BaseException;
import com.example.demo.src.inquiry.model.CreateInquiryReq;
import com.example.demo.src.keyword.model.KeyWordAlarmRes;
import com.example.demo.src.keyword.model.KeyWordCreateReq;
import com.example.demo.src.myPage.model.MyPageZzim;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class KeyWordProvider {

    private final KeyWordDao keyWordDao;
    private final JwtService jwtService;

    @Autowired
    public KeyWordProvider(KeyWordDao keyWordDao, JwtService jwtService) {
        this.keyWordDao = keyWordDao;
        this.jwtService = jwtService;
    }

    @Transactional
    public List<KeyWordAlarmRes> getMyKeyWordAlarm(int userIdx) throws BaseException {
        try {
            List<KeyWordAlarmRes> keyWordAlarmRes = new ArrayList<>();
            List<String> keyWords = keyWordDao.getKeyWords(userIdx);
            for (String keyWord : keyWords) {
                keyWordAlarmRes.addAll(keyWordDao.getKeyWordAlarm(keyWord));
            }
            //List<KeyWordAlarmRes> keyWordAlarmRes = keyWordDao.getMyKeyWordAlarm(userIdx);
            return keyWordAlarmRes;
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
