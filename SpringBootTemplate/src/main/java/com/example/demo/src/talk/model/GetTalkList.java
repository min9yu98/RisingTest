package com.example.demo.src.talk.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetTalkList {
    String lastMessage;
    String updatedAt;
    int talkRoomIdx;
    String postUserName;
    int postUserIdx;
    String postUserImg_Url;
}
