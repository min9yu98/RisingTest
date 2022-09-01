package com.example.demo.src.inquiry;

import com.example.demo.config.BaseException;
import com.example.demo.src.inquiry.model.GetMyInquiryRes;
import com.example.demo.src.inquiry.model.Inquiry;
import com.example.demo.src.inquiry.model.InquirySpecificRes;
import com.example.demo.utils.JwtService;
import com.sun.xml.bind.v2.runtime.reflect.NullSafeAccessor;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import sun.tools.jconsole.inspector.XObject;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class InquiryProvider {

    private final InquiryDao inquiryDao;
    private final JwtService jwtService;

    @Autowired

    public InquiryProvider(InquiryDao inquiryDao, JwtService jwtService) {
        this.inquiryDao = inquiryDao;
        this.jwtService = jwtService;
    }

    public List<GetMyInquiryRes> getMyInquiry(int userIdx) throws BaseException {
        try {
            return inquiryDao.getMyInquiry(userIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public InquirySpecificRes getInquirySpecific(int inquiryIdx) throws BaseException {
        try {
            InquirySpecificRes getInquirySpecific =  inquiryDao.getInquirySpecific(inquiryIdx);
            if (getInquirySpecific.getAnswerStatus().equals("답변 완료")){
                getInquirySpecific.setAnswerCreatedAt(inquiryDao.getAnswerCreatedAt(inquiryIdx));
                getInquirySpecific.setAnswerDescription(inquiryDao.getAnswerDescription(inquiryIdx));
            }
            return getInquirySpecific;
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<Inquiry> getInquiry() throws BaseException {
        try {
            return inquiryDao.getInquiry();

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
