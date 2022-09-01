package com.example.demo.src.inquiry;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.inquiry.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/inquiry")
public class InquiryController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final InquiryProvider inquiryProvider;
    @Autowired
    private final InquiryService inquiryService;
    @Autowired
    private final JwtService jwtService;

    public InquiryController(InquiryProvider inquiryProvider, InquiryService inquiryService, JwtService jwtService) {
        this.inquiryProvider = inquiryProvider;
        this.inquiryService = inquiryService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("/create/{userIdx}")
    public BaseResponse<String> createInquiry(@RequestBody CreateInquiryReq createInquiryReq,
                                            @PathVariable("userIdx") int userIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            inquiryService.createInquiry(createInquiryReq, userIdx);
            return new BaseResponse<String>("고객문의가 등록되었습니다");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetMyInquiryRes>> getMyInquiry(@PathVariable("userIdx") int userIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            List<GetMyInquiryRes> getMyInquiryRes = inquiryProvider.getMyInquiry(userIdx);
            return new BaseResponse<>(getMyInquiryRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @GetMapping("/specific/{userIdx}/{inquiryIdx}")
    public BaseResponse<InquirySpecificRes> createInquiry(@PathVariable("userIdx") int userIdx,
                                              @PathVariable("inquiryIdx") int inquiryIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            InquirySpecificRes inquirySpecificRes = inquiryProvider.getInquirySpecific(inquiryIdx);
            return new BaseResponse<>(inquirySpecificRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<Inquiry>> getInquiry()
    {
        try {
            List<Inquiry> inquiries = inquiryProvider.getInquiry();
            return new BaseResponse<>(inquiries);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 답변 //

    @ResponseBody
    @PostMapping("/create/answer/{inquiryIdx}")
    public BaseResponse<String> getMyInquiry(@RequestBody CreateAnswerReq createAnswerReq,
                                             @PathVariable("inquiryIdx") int inquiryIdx)
    {
        try {
            inquiryService.createAnswer(createAnswerReq,inquiryIdx);
            return new BaseResponse<>("답변이 완료되었습니다");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{userIdx}/{inquiryIdx}")
    public BaseResponse<String> getMyInquiry(@PathVariable("userIdx") int userIdx,
                                             @PathVariable("inquiryIdx") int inquiryIdx)
    {
        try {
            inquiryService.deleteInquiry(userIdx,inquiryIdx);
            return new BaseResponse<>("문의 사항이 삭제 되었습니다");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
