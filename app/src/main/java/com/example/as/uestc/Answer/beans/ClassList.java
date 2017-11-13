package com.example.as.uestc.Answer.beans;

import java.util.List;

/**
 * 所有展示班级的信息
 * Created by as on 2017/11/5.
 */

public class ClassList {
    private int errcode;
    private String errmsg;
    private List<Info> info;
    public int getErrcode()
    {
        return this.errcode;
    }
    public String getErrmsg()
    {
        return this.errmsg;
    }

    public List<Info> getInfo()
    {
        return this.info;
    }

}

