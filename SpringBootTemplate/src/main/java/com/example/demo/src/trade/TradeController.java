package com.example.demo.src.trade;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.trade.model.PostTradeRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/trades")
public class TradeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @ResponseBody
    @PostMapping("/{userIdx}/{postIdx}")
    public BaseResponse<PostTradeRes> trade(@PathVariable("userIdx") long userIdx, @PathVariable("postIdx") long postIdx){
        try {
            PostTradeRes postTradeRes = tradeService.trade(userIdx, postIdx);
            return new BaseResponse<>(postTradeRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
