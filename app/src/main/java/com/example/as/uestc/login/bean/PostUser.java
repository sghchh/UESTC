package com.example.as.uestc.login.bean;

import java.io.Serializable;

/**
 * 登录请求的时候提交的数据类型
 * Created by as on 2017/11/5.
 */

public class PostUser implements Serializable{
    private String name;
    private String password;
    public PostUser(String name,String password)
    {
        this.name=name;
        this.password=password;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public void setPassword(String password)
    {
        this.password=password;
    }

    public String getName()
    {
        return this.name;
    }

    public String getPassword()
    {
        return this.password;
    }
}
