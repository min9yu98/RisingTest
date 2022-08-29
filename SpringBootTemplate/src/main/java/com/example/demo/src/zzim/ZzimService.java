package com.example.demo.src.zzim;

import com.example.demo.config.BaseException;
import com.example.demo.src.inquiry.model.CreateInquiryReq;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ZzimService {

    private final ZzimDao zzimDao;
    private final JwtService jwtService;

    @Autowired
    public ZzimService(ZzimDao zzimDao, JwtService jwtService) {
        this.zzimDao = zzimDao;
        this.jwtService = jwtService;
    }


    @Transactional
    public void createZzim(int userIdx, int postIdx) throws BaseException {
        try {
            zzimDao.createZzim(userIdx, postIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteZzim(int userIdx, int postIdx) throws BaseException {
        try {
            zzimDao.deleteZzim(userIdx, postIdx);

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
