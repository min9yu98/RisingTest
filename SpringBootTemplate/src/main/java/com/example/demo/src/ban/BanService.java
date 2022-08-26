package com.example.demo.src.ban;

import com.example.demo.config.BaseException;
import com.example.demo.src.ban.model.PostBanRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.POST_BAN_SECESSION_USER;

@Service
public class BanService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BanDao banDao;

    @Autowired
    private final BanProvider banProvider;

    public BanService(BanDao banDao, BanProvider banProvider) {
        this.banDao = banDao;
        this.banProvider = banProvider;
    }

    public PostBanRes banUser(long banUserIdx, long bannedUserIdx) throws BaseException {
        if (banDao.checkStore(bannedUserIdx) == 1) throw new BaseException(POST_BAN_SECESSION_USER);
        try {
            if (banDao.checkBan(banUserIdx, bannedUserIdx) == 1) {
                return new PostBanRes(banDao.banExist(banUserIdx, bannedUserIdx));
            } else {
                return new PostBanRes(banDao.banUser(banUserIdx, bannedUserIdx));
            }
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int cancelBan(long banUserIdx, long bannedUserIdx) throws BaseException{
        if (banDao.checkStore(bannedUserIdx) == 1) throw new BaseException(POST_BAN_SECESSION_USER);
        try {
            return banDao.cancelBan(banUserIdx, bannedUserIdx);
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
