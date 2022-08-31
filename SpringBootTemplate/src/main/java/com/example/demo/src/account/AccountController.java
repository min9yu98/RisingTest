package com.example.demo.src.account;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.account.model.*;
import com.example.demo.src.brand.model.GetBrandPostRes;
import com.example.demo.src.brand.model.PatchDeleteBrandReq;
import com.example.demo.src.brand.model.PostBrandRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/accounts")
public class AccountController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AccountProvider accountProvider;
    @Autowired
    private final AccountService accountService;

    public AccountController(AccountProvider accountProvider, AccountService accountService){
        this.accountProvider = accountProvider;
        this.accountService = accountService;
    }

    @ResponseBody
    @PostMapping("/new/{userIdx}")
    public BaseResponse<PostAccountRes> registerAccount(@RequestBody PostAccountReq postAccountReq, @PathVariable("userIdx") long userIdx){
        try {
            PostAccountRes postAccountRes = accountService.registerAccount(postAccountReq, userIdx);
            return new BaseResponse<>(postAccountRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{userIdx}/delete/{accountIdx}")
    public BaseResponse<String> deleteAccount(@PathVariable("userIdx") long userIdx, @PathVariable("accountIdx") long accountIdx){
        try {
            PatchDeleteAccountReq patchDeleteAccountReq = new PatchDeleteAccountReq(accountIdx, userIdx);
            int result = accountService.deleteAccount(patchDeleteAccountReq);

            String resultMessage = "계좌가 삭제되었습니다.";
            return new BaseResponse<>(resultMessage);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetAccountRes>> getAccounts(@PathVariable("userIdx") long userIdx){
        try {
            List<GetAccountRes>getAccountRes = accountProvider.getAccounts(userIdx);
            return new BaseResponse<>(getAccountRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{userIdx}/edit/{accountIdx}")
    public BaseResponse<String> editAccount(@RequestBody PatchEditAccountReq patchEditAccountReq,
                                            @PathVariable("userIdx") long userIdx,
                                            @PathVariable("accountIdx") long accountIdx){
        try{
            int result = accountService.editAccount(patchEditAccountReq, userIdx, accountIdx);
            String resultMessage = "계좌가 수정되었습니다.";
            return new BaseResponse<>(resultMessage);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
