package com.example.demo.src.keyword;

import com.example.demo.config.BaseException;
import com.example.demo.src.keyword.model.KeyWordCreateReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class KeyWordService {

    private final KeyWordDao keyWordDao;
    private final JwtService jwtService;

    @Autowired
    public KeyWordService(KeyWordDao keyWordDao, JwtService jwtService) {
        this.keyWordDao = keyWordDao;
        this.jwtService = jwtService;
    }

    @Transactional
    public void createKeyWord(KeyWordCreateReq keyWordCreateReq, int userIdx) throws BaseException {
        try {
            keyWordDao.createKeyWord(keyWordCreateReq, userIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteKeyWord(int userIdx,String keyWord) throws BaseException {
        try {
            keyWordDao.deleteKeyWord(userIdx,keyWord);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
