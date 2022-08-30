package com.example.demo.src.notice;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.notice.model.GetNoticeRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/notices")
public class NoticeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final NoticeProvider noticeProvider;
    @Autowired
    private final NoticeService noticeService;

    public NoticeController(NoticeProvider noticeProvider, NoticeService noticeService) {
        this.noticeProvider = noticeProvider;
        this.noticeService = noticeService;
    }

    @ResponseBody
    @GetMapping("/{pageNum}")
    public BaseResponse<List<GetNoticeRes>> getNotices(@PathVariable("pageNum") long pageNum){
        try{
            List<GetNoticeRes> getNoticeRes = noticeProvider.getNotices(pageNum);
            return new BaseResponse<>(getNoticeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{category}/{pageNum}")
    public BaseResponse<List<GetNoticeRes>> getNotice(@PathVariable("category") String category,
                                                      @PathVariable("pageNum") long pageNum){
        try{
            List<GetNoticeRes> getNoticeRes = noticeProvider.getNotice(category, pageNum);
            return new BaseResponse<>(getNoticeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
