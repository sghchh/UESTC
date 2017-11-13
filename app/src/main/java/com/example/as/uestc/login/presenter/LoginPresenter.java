package com.example.as.uestc.login.presenter;

import com.example.as.uestc.base.mvp.presenter.MVPPresenter;
import com.example.as.uestc.login.bean.PostUser;

/**
 * Created by as on 2017/11/5.
 */

public interface LoginPresenter extends MVPPresenter {
    void login(PostUser user);
}
