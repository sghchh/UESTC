package com.example.as.uestc.login.model;

import com.example.as.uestc.base.mvp.model.MVPModel;
import com.example.as.uestc.login.bean.Login;
import com.example.as.uestc.login.bean.PostUser;

import io.reactivex.Observable;

/**
 * Created by as on 2017/11/5.
 */

public interface LoginModel extends MVPModel {
    Observable<Login> getLogin(PostUser user);
}
