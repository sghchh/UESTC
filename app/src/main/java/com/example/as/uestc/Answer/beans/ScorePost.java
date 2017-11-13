package com.example.as.uestc.Answer.beans;

import java.io.Serializable;

/**
 * 打分的时候上传的数据
 * Created by as on 2017/11/8.
 */

public class ScorePost implements Serializable{
    private String classID;
    private String score;
    private String token;

    public ScorePost(String classID, String score, String token){
        this.classID=classID;
        this.score=score;
        this.token=token;

    }

    public void setClassID(String classID)
    {
        this.classID=classID;
    }

    public void setScore(String score)
    {
        this.score=score;
    }

    public void setToken(String token)
    {
        this.token=token;
    }

    public String getClassID()
    {
        return this.classID;
    }

    public String getScore()
    {
        return this.score;
    }

    public String getToken()
    {
        return this.token;
    }
}
