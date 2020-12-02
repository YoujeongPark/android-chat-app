package com.example.chat_app;

import java.io.Serializable;

public class ChatData implements Serializable {

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    private String msg;
    private String nickName;



}
