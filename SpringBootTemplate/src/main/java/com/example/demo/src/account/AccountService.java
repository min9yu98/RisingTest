package com.example.demo.src.account;

import com.example.demo.config.BaseException;
import com.example.demo.src.account.model.PatchDeleteAccountReq;
import com.example.demo.src.account.model.PatchEditAccountReq;
import com.example.demo.src.account.model.PostAccountReq;
import com.example.demo.src.account.model.PostAccountRes;
import com.example.demo.src.brand.model.PostBrandRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class AccountService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AccountDao accountDao;
    @Autowired
    private final AccountProvider accountProvider;

    public AccountService(AccountDao accountDao, AccountProvider accountProvider) {
        this.accountDao = accountDao;
        this.accountProvider = accountProvider;
    }

    public PostAccountRes registerAccount(PostAccountReq postAccountReq, long userIdx) throws BaseException {
        if (accountDao.checkAccountNum(userIdx) > 2){
            throw new BaseException(POST_OVER_ACCOUNT_QUANTITY);
        }
        try {
            if(postAccountReq.getAccountHolder() == null){
                throw new BaseException(POST_ACCOUNT_EMPTY_ACCOUNT_HOLDER);
            }
            if(postAccountReq.getBank() == null){
                throw new BaseException(POST_ACCOUNT_EMTPY_BANK);
            }
            if(postAccountReq.getAccountNum() == null) {
                throw new BaseException(POST_ACCOUNT_EMPTY_ACCOUNT_NUM);
            }
            long accountIdx = accountDao.registerAccount(postAccountReq, userIdx);

            return new PostAccountRes(accountIdx);
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int deleteAccount(PatchDeleteAccountReq patchDeleteAccountReq) throws BaseException{
        if (accountDao.checkDelete(patchDeleteAccountReq.getAccountIdx()) == 1){
            throw new BaseException(PATCH_ALREADY_DELETED_ACCOUNT);
        }
        try {
            int result = accountDao.deleteAccount(patchDeleteAccountReq);
            return result;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int editAccount(PatchEditAccountReq patchEditAccountReq, long userIdx, long accountIdx) throws BaseException{
        try {
            int result = accountDao.editAccount(patchEditAccountReq, userIdx, accountIdx);
            return result;
        } catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
