package com.example.as.uestc.Answer.presenter;

import android.app.Activity;

import com.example.as.uestc.base.mvp.model.BaseModel;
import com.example.as.uestc.base.mvp.presenter.BasePresenter;
import com.example.as.uestc.base.mvp.view.BaseView;

/**
 * Created by as on 2017/11/5.
 */

abstract class AnswerPre implements BasePresenter {

    protected BaseView mAnswerView;
    protected BaseModel mAnswerModel;

    protected Activity activity;
    @Override
    public void attach(BaseView baseView, BaseModel baseModel) {
        this.mAnswerModel=baseModel;
        this.mAnswerView=baseView;
        baseView.getListener().attachPresenter(this);
    }

    public BaseModel getModel() {
        return mAnswerModel;
    }

    @Override
    public BaseView getView() {
        return mAnswerView;
    }
}
