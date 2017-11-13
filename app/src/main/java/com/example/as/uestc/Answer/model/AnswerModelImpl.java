package com.example.as.uestc.Answer.model;

import com.example.as.uestc.Answer.beans.ClassList;
import com.example.as.uestc.Answer.beans.CurrentClass;
import com.example.as.uestc.Answer.beans.ScorePost;
import com.example.as.uestc.Answer.beans.ScoreRes;
import com.example.as.uestc.Answer.networks.RetrofitManager;

import io.reactivex.Observable;

/**
 * Created by as on 2017/11/5.
 */

public class AnswerModelImpl extends AnswerModel {
    private RetrofitManager manager;
    private ClassList current;
    public AnswerModelImpl()
    {
        this.manager=RetrofitManager.getInstance();
    }
    public Observable<ClassList> getCurrentClass()
    {
        return this.manager.getCurrentClass();
    }

    public Observable<CurrentClass> getCurrent(String classID)
    {
        return this.manager.getCurrent(classID);
    }

    public Observable<ScoreRes> getPostRes(ScorePost scorePost)
    {
        return this.manager.getPostRes(scorePost);
    }

}
