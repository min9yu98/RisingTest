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

    public List<GetBrandRes> getBrandKor() throws BaseException {
        try{
            List<GetBrandRes> getBrandRes = brandDao.getBrandKor();
            return getBrandRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
