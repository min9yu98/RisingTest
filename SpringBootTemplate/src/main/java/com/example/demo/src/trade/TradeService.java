package com.example.demo.src.trade;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.trade.model.PostTradeRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class TradeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final TradeDao tradeDao;

    @Autowired
    private final TradeProvider tradeProvider;

    public TradeService(TradeDao tradeDao, TradeProvider tradeProvider) {
        this.tradeDao = tradeDao;
        this.tradeProvider = tradeProvider;
    }

    public PostTradeRes trade(long userIdx, long postIdx) throws BaseException {
        long resultPostIdx;
        try {
            if (tradeProvider.checkPresence(postIdx) == 1){
                resultPostIdx = tradeDao.trade(userIdx, postIdx, false);
            } else{
                resultPostIdx = tradeDao.trade(userIdx, postIdx, true);
            }
            return new PostTradeRes(resultPostIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
