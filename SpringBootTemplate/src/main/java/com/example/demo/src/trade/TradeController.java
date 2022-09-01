package com.example.demo.src.trade;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.trade.model.PostTradeRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.INVALID_USER_JWT;

@RestController
@RequestMapping("/app/trades")
public class TradeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final TradeService tradeService;

    @Autowired
    private final JwtService jwtService;
    public TradeController(TradeService tradeService, JwtService jwtService) {
        this.tradeService = tradeService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @PostMapping("/{userIdx}/{postIdx}")
    public BaseResponse<PostTradeRes> trade(@PathVariable("userIdx") long userIdx, @PathVariable("postIdx") long postIdx){
        try {
//            int userIdxByJwt = jwtService.getUserIdx();
//            //userIdx와 접근한 유저가 같은지 확인 !!!!
//            if(userIdx != userIdxByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }
            PostTradeRes postTradeRes = tradeService.trade(userIdx, postIdx);
            return new BaseResponse<>(postTradeRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
