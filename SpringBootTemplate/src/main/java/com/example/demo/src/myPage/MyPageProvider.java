package com.example.demo.src.myPage;


import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyPageProvider {

    private final MyPageDao myPageDao;
    private final JwtService jwtService;

    @Autowired

    public MyPageProvider(MyPageDao myPageDao, JwtService jwtService) {
        this.myPageDao = myPageDao;
        this.jwtService = jwtService;
    }

}
