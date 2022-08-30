package com.example.demo.src.notice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private final NoticeDao noticeDao;
    @Autowired
    public NoticeService(NoticeDao noticeDao) {
        this.noticeDao = noticeDao;
    }

}
