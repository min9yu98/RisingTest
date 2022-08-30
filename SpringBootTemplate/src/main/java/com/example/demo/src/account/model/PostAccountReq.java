package com.example.demo.src.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostAccountReq {
    private String accountHolder;
    private String bank;
    private String accountNum;
    private boolean defaultAccount;
}
