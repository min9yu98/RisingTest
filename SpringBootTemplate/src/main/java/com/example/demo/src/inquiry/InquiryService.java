package com.example.demo.src.inquiry;

import com.example.demo.config.BaseException;
import com.example.demo.src.inquiry.model.CreateAnswerReq;
import com.example.demo.src.inquiry.model.CreateInquiryReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class InquiryService {

    private final InquiryDao inquiryDao;
    private final JwtService jwtService;

    @Autowired
    public InquiryService(InquiryDao inquiryDao, JwtService jwtService) {
        this.inquiryDao = inquiryDao;
        this.jwtService = jwtService;
    }

    @Transactional
    public void createInquiry(CreateInquiryReq createInquiryReq, int userIdx) throws BaseException {
        try {
                inquiryDao.createInquiry(createInquiryReq, userIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void createAnswer(CreateAnswerReq createAnswerReq,int inquiryIdx) throws BaseException {
        try {
            inquiryDao.createAnswer(createAnswerReq,inquiryIdx);
            inquiryDao.patchInquiryStatus(inquiryIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteInquiry(int userIdx, int inquiryIdx) throws BaseException {
        try {
            inquiryDao.deleteInquiry(userIdx,inquiryIdx);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
