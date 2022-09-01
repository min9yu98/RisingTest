package com.example.demo.src.Follow;

import com.example.demo.config.BaseException;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class FollowService {

    private final FollowDao followDao;
    private final JwtService jwtService;

    @Autowired
    public FollowService(FollowDao followDao, JwtService jwtService) {
        this.followDao = followDao;
        this.jwtService = jwtService;
    }

    @Transactional
    public void createFollow(int userIdx, int postUserIdx) throws BaseException {
        try {
            followDao.createFollow(userIdx, postUserIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteFollow(int userIdx, int postUserIdx) throws BaseException {
        try {
            followDao.deleteFollow(userIdx, postUserIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
