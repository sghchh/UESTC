package com.example.as.uestc.login.view;

import com.example.as.uestc.base.mvp.view.MVPView;
import com.example.as.uestc.login.bean.Login;

/**
 * Created by as on 2017/11/5.
 */

public interface LoginView extends MVPView {
    void finishLogin(Login login);
}
