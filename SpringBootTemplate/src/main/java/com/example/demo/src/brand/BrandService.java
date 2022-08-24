package com.example.demo.src.brand;

import com.example.demo.config.BaseException;
import com.example.demo.src.brand.model.PatchDeleteBrandReq;
import com.example.demo.src.brand.model.PostBrandReq;
import com.example.demo.src.brand.model.PostBrandRes;
import com.example.demo.src.post.PostDao;
import com.example.demo.src.post.PostProvider;
import com.example.demo.src.post.model.post.PostPostRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BrandService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final BrandDao brandDao;
    @Autowired
    private final BrandProvider brandProvider;
    public BrandService(BrandDao brandDao, BrandProvider brandProvider) {
        this.brandDao = brandDao;
        this.brandProvider = brandProvider;
    }
    public PostBrandRes registerBrand(PostBrandReq postBrandReq) throws BaseException {
        try {
            long brandIdx = brandDao.registerBrand(postBrandReq);
            return new PostBrandRes(brandIdx);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int deleteBrand(PatchDeleteBrandReq patchDeleteBrandReq) throws BaseException {
        try{
            int result = brandDao.deleteBrand(patchDeleteBrandReq);
            return result;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
