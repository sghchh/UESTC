package com.example.as.uestc.login.model;

import com.example.as.uestc.login.bean.Login;
import com.example.as.uestc.login.bean.PostUser;
import com.example.as.uestc.login.networks.LoginManager;
import com.google.gson.Gson;

import io.reactivex.Observable;

/**
 * Created by as on 2017/11/5.
 */

public class LoginModelImpl implements LoginModel {
    private LoginManager manager;
    private Gson gson;

    public LoginModelImpl()
    {
        this.gson=new Gson();
        this.manager=LoginManager.getInstance();
    }

    @Override
    public Observable<Login> getLogin(PostUser user) {
        return manager.login(user);
    }
}
