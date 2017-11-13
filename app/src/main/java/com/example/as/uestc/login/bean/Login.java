package com.example.as.uestc.login.bean;

/**
 * 登录成功返回的数据
 * Created by as on 2017/11/5.
 */

public class Login {
    private int errcode;
    private String errmsg;

    private String name;
    private String token;

    public int getErrcode()
    {
        return this.errcode;
    }

    public String getErrmsg()
    {
        return this.errmsg;
    }

    public String getName()
    {
        return this.name;
    }

    public String getToken()
    {
        return this.token;
    }
}
