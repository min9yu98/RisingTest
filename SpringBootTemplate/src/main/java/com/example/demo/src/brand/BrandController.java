package com.example.demo.src.brand;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.brand.model.*;
import com.example.demo.src.post.model.post.PostPostReq;
import com.example.demo.src.post.model.post.PostPostRes;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.BaseResponseStatus.POST_POST_EMPTY_PRICE;

@RestController
@RequestMapping("/app/brands")
public class BrandController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final BrandProvider brandProvider;
    @Autowired
    private final BrandService brandService;
    @Autowired
    private final JwtService jwtService;


    public BrandController(BrandProvider brandProvider, BrandService brandService, JwtService jwtService) {
        this.brandProvider = brandProvider;
        this.brandService = brandService;
        this.jwtService = jwtService;
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
    @PostMapping("/{userIdx}/follow/{brandIdx}")
    public BaseResponse<PostFollowRes> followBrand(@PathVariable("userIdx") long userIdx,
                                                   @PathVariable("brandIdx") long brandIdx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인 !!!!
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PostFollowRes postFollowRes = brandService.followBrand(userIdx, brandIdx);
            return new BaseResponse<>(postFollowRes);
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

    @ResponseBody
    @PatchMapping("/{userIdx}/follow-cancel/{brandIdx}")
    public BaseResponse<String> cancelFollow(@PathVariable("userIdx") long userIdx, @PathVariable("brandIdx") long brandIdx){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인 !!!!
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            brandService.cancelFollow(userIdx, brandIdx);

            String resultMessage = "팔로우가 취소되었습니다.";
            return new BaseResponse<>(resultMessage);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 브랜드별 게시글 조회
    @ResponseBody
    @GetMapping("/{brandIdx}/{userIdx}/posts/{pageNum}")
    public BaseResponse<List<GetBrandPostRes>> getBrandPost(@PathVariable("brandIdx") long brandIdx,
                                                      @PathVariable("userIdx") long userIdx,
                                                      @PathVariable("pageNum") long pageNum){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인 !!!!
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetBrandPostRes>getBrandPostRes = brandProvider.getBrandPost(brandIdx, userIdx, pageNum);
            return new BaseResponse<>(getBrandPostRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // 가나다순
    @ResponseBody
    @GetMapping("/order/{lang}")
    public BaseResponse<List<GetBrandRes>> getBrand(@PathVariable("lang") String lang){
        try{
            List<GetBrandRes> getBrandRes = brandProvider.getBrand(lang);
            return new BaseResponse<>(getBrandRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/order/{lang}/{userIdx}")
    public BaseResponse<List<GetBrandRes>> getMyBrand(@PathVariable("lang") String lang, @PathVariable("userIdx") long userIdx){
        try{
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인 !!!!
            if(userIdx != userIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            List<GetBrandRes> getBrandRes = brandProvider.getMyBrand(lang, userIdx);
            return new BaseResponse<>(getBrandRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
