package com.example.as.uestc.Answer.presenter;

import com.example.as.uestc.base.mvp.EventListener;
import com.example.as.uestc.base.mvp.presenter.BasePresenter;

/**
 * Created by as on 2017/11/5.
 */

abstract class AnswerListener implements EventListener {
    protected BasePresenter presenter;
    public void attachPresenter(BasePresenter answerPre)
    {
        this.presenter=answerPre;
    }
    public BasePresenter getPresenter()
    {
        return this.presenter;
    }
}
