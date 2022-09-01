package com.example.demo.src.talk;

import com.example.demo.config.BaseException;
import com.example.demo.src.inquiry.model.Inquiry;
import com.example.demo.src.talk.model.GetSpecificTalkList;
import com.example.demo.src.talk.model.GetTalkList;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class TalkProvider {

    private final TalkDao talkDao;
    private final JwtService jwtService;

    @Autowired
    public TalkProvider(TalkDao talkDao, JwtService jwtService) {
        this.talkDao = talkDao;
        this.jwtService = jwtService;
    }

    @Transactional
    public List<GetTalkList> getTalkList(int userIdx) throws BaseException {
        try {
            List<GetTalkList> getTalkList = talkDao.getTalkListBySender(userIdx);
            getTalkList.addAll(talkDao.getTalkListByReceiver(userIdx));
            return getTalkList;
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetSpecificTalkList> getSpecificTalkList(int talkRoomIdx) throws BaseException {
        try {
            List<GetSpecificTalkList> getSpecificTalkList = talkDao.getSpecificTalkList(talkRoomIdx);
            return getSpecificTalkList;
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
