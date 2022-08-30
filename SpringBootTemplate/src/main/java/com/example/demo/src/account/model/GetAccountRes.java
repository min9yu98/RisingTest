package com.example.demo.src.account.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountRes {
    private long accountIdx;
    private String roll;
    private String bank;
    private String accountNum;
    private String accountHolder;
}
