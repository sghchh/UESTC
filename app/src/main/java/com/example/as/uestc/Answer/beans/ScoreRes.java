package com.example.as.uestc.Answer.beans;

import java.io.Serializable;

/**
 * 打分结束后获得的状态码
 * Created by as on 2017/11/8.
 */

public class ScoreRes implements Serializable{
    private int errcode;
    private String errmsg;

    public int getErrcode()
    {
        return this.errcode;
    }

    public String getErrmsg()
    {
        return this.errmsg;
    }
}
