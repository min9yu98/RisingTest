package com.example.demo.src.brand;

import com.example.demo.config.BaseException;
import com.example.demo.src.brand.model.GetBrandRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class BrandProvider {
    private final BrandDao brandDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public BrandProvider(BrandDao brandDao){
        this.brandDao = brandDao;
    }

    public List<GetBrandRes> getBrand(String lang) throws BaseException {
        boolean flag = false;
        try{
            if (lang.equals("kor") || lang.equals("\"kor\"")){
                flag = true;
            } else if (lang.equals("eng") || lang.equals("\"eng\"")){
                flag = false;
            }
            return brandDao.getBrand(flag);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetBrandRes> getMyBrand(String lang, long userIdx) throws BaseException {
        boolean flag = false;
        try{
            if (lang.equals("kor") || lang.equals("\"kor\"")){
                flag = true;
            } else if (lang.equals("eng") || lang.equals("\"eng\"")){
                flag = false;
            }
            return brandDao.getMyBrand(flag, userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
