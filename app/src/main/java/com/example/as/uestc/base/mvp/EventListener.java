package com.example.as.uestc.base.mvp;

import com.example.as.uestc.base.mvp.presenter.BasePresenter;

/**
 * Created by as on 2017/11/5.
 */

public interface EventListener {
    void attachPresenter(BasePresenter basePresenter);
    void callPresenterToRefreshFragment(String classID);
}
