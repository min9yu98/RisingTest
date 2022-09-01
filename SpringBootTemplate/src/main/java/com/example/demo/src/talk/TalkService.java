package com.example.demo.src.talk;

import com.example.demo.config.BaseException;
import com.example.demo.src.talk.model.MessageReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class TalkService {

    private final TalkDao talkDao;
    private final JwtService jwtService;

    @Autowired
    public TalkService(TalkDao talkDao, JwtService jwtService) {
        this.talkDao = talkDao;
        this.jwtService = jwtService;
    }

    @Transactional
    public void createTalk(MessageReq messageReq,int userIdx, int postUserIdx) throws BaseException {
        try {
            int roomIdx;
            if (talkDao.isRoom(userIdx,postUserIdx) == 0){
                roomIdx = talkDao.createRoom(userIdx);
            }
            else {
                roomIdx = talkDao.getRoomIdx(userIdx, postUserIdx);
            }
            talkDao.createTalk(messageReq,userIdx, postUserIdx,roomIdx);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
