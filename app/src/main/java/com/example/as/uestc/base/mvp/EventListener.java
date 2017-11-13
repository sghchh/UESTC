package com.example.as.uestc.base.mvp;

import com.example.as.uestc.base.mvp.presenter.BasePresenter;

import java.io.Serializable;

/**
 * Created by as on 2017/11/5.
 */

public interface EventListener extends Serializable{
    void attachPresenter(BasePresenter basePresenter);
    void callPresenterToRefreshFragment(String classID);
}
