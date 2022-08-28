package com.example.demo.src.trade;

import com.example.demo.config.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class TradeProvider {
    private final TradeDao tradeDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TradeProvider(TradeDao tradeDao) {
        this.tradeDao = tradeDao;
    }

    public int checkPresence(long postIdx) throws BaseException{
        boolean flag = false;
        try {
            return tradeDao.checkPresence(postIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
