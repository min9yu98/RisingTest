package com.example.demo.src.ban;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.ban.model.PostBanRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.PATCH_EMPTY_BAN;
import static com.example.demo.config.BaseResponseStatus.POST_BAN_SECESSION_USER;

@RestController
@RequestMapping("/app/ban")
public class BanController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final BanProvider banProvider;

    @Autowired
    private final BanService banService;

    public BanController(BanProvider banProvider, BanService banService) {
        this.banProvider = banProvider;
        this.banService = banService;
    }

    @ResponseBody
    @PostMapping("/{banUserIdx}/ban/{bannedUserIdx}")
    public BaseResponse<PostBanRes> banUser(@PathVariable("banUserIdx") long banUserIdx,
                                            @PathVariable("bannedUserIdx") long bannedUserIdx){
        try {
            PostBanRes postBanRes = banService.banUser(banUserIdx, bannedUserIdx);
            return new BaseResponse<>(postBanRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{banUserIdx}/ban-cancel/{bannedUserIdx}")
    public BaseResponse<String> cancelBan(@PathVariable("banUserIdx") long banUserIdx,
                                          @PathVariable("bannedUserIdx") long bannedUserIdx){
        try{
            int result = banService.cancelBan(banUserIdx, bannedUserIdx);

            if (result == 0) return new BaseResponse<>(PATCH_EMPTY_BAN);

            String resultMessage = "상점 차단이 취소되었습니다.";
            return new BaseResponse<>(resultMessage);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
