package com.example.demo.src.brand;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.brand.model.GetBrandRes;
import com.example.demo.src.brand.model.PatchDeleteBrandReq;
import com.example.demo.src.brand.model.PostBrandReq;
import com.example.demo.src.brand.model.PostBrandRes;
import com.example.demo.src.post.model.post.PostPostReq;
import com.example.demo.src.post.model.post.PostPostRes;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.BaseResponseStatus.POST_POST_EMPTY_PRICE;

@RestController
@RequestMapping("/app/brand")
public class BrandController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final BrandProvider brandProvider;
    @Autowired
    private final BrandService brandService;


    public BrandController(BrandProvider brandProvider, BrandService brandService) {
        this.brandProvider = brandProvider;
        this.brandService = brandService;
    }

    @ResponseBody
    @PostMapping("/new")
    public BaseResponse<PostBrandRes> registerBrand(@RequestBody PostBrandReq postBrandReq)
    {
        if (postBrandReq.getBrandImg_url() == null){
            return new BaseResponse<>(POST_BRAND_EMPTY_IMG);
        }
        if (postBrandReq.getBrandName() == null) {
            return new BaseResponse<>(POST_BRAND_EMPTY_NAME);
        }
        if (postBrandReq.getBrandEngName() == null) {
            return new BaseResponse<>(POST_BRAND_EMPTY_ENG_NAME);
        }

        try {
            PostBrandRes postBrandRes = brandService.registerBrand(postBrandReq);
            return new BaseResponse<>(postBrandRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{brandName}")
    public BaseResponse<String> deleteBrand(@PathVariable("brandName") String brandName){
        try {
            if (brandName.equals("\"" + brandName.substring(1, brandName.length() - 1) + "\""))
                brandName = brandName.substring(1, brandName.length() - 1);

            PatchDeleteBrandReq patchDeleteBrandReq = new PatchDeleteBrandReq(brandName);
            int result = brandService.deleteBrand(patchDeleteBrandReq);

            if (result == 0) return new BaseResponse<>(PATCH_DELETE_FAIL_BRAND);

            String resultMessage = "브랜드가 삭제되었습니다.";
            return new BaseResponse<>(resultMessage);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 가나다순
    @ResponseBody
    @GetMapping("/order-kor")
    public BaseResponse<List<GetBrandRes>> getBrandKor(){
        try{
            List<GetBrandRes> getBrandRes = brandProvider.getBrandKor();
            return new BaseResponse<>(getBrandRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
