package com.example.demo.src.zzim;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/zzim")
public class ZzimController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final ZzimProvider zzimProvider;
    @Autowired
    private final ZzimService zzimService;
    @Autowired
    private final JwtService jwtService;

    public ZzimController(ZzimProvider zzimProvider, ZzimService zzimService, JwtService jwtService) {
        this.zzimProvider = zzimProvider;
        this.zzimService = zzimService;
        this.jwtService = jwtService;
    }
    @ResponseBody
    @PostMapping("/create/{userIdx}/{postIdx}")
    public BaseResponse<String> createZzim(@PathVariable("userIdx") int userIdx,
                                              @PathVariable("postIdx") int postIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            zzimService.createZzim(userIdx,postIdx);
            return new BaseResponse<>("찜하기가 완료 되었습니다");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/delete/{userIdx}/{postIdx}")
    public BaseResponse<String> deleteZzim(@PathVariable("userIdx") int userIdx,
                                           @PathVariable("postIdx") int postIdx)
    {
        try {
//            //jwt에서 idx 추출.
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            zzimService.deleteZzim(userIdx,postIdx);
            return new BaseResponse<>("찜하기가 취소 되었습니다");
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
