package com.example.demo.src.account;

import com.example.demo.config.BaseException;
import com.example.demo.src.account.model.GetAccountRes;
import com.example.demo.src.brand.BrandDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class AccountProvider {
    private final AccountDao accountDao;
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AccountProvider(AccountDao accountDao){
        this.accountDao = accountDao;
    }
    public List<GetAccountRes> getAccounts(long userIdx) throws BaseException{
        try{
            return accountDao.getAccounts(userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
