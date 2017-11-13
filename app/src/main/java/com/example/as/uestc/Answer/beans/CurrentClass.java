package com.example.as.uestc.Answer.beans;

import java.io.Serializable;
import java.util.List;

/**
 * 当前班级的信息
 * Created by as on 2017/11/8.
 */

public class CurrentClass implements Serializable{
    private String errcode;
    private String errmsg;
    private int orderNum;
    private String classID;
    private String academy;
    private String cover;
    private List<String> images;

    public String getErrcode()
    {
        return this.errcode;
    }

    public String getErrmsg()
    {
        return this.errmsg;
    }

    public String getClassID()
    {
        return this.classID;
    }

    public String getAcademy()
    {
        return this.academy;
    }

    public int getOrderNum()
    {
        return this.orderNum;
    }

    public List<String> getImages()
    {
        return this.images;
    }
}
