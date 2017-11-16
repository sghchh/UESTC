package com.example.as.uestc.login.presenter;

import android.util.Log;
import android.widget.TextView;

import com.example.as.uestc.login.LoginActivity;
import com.example.as.uestc.login.bean.Login;
import com.example.as.uestc.login.bean.PostUser;
import com.example.as.uestc.login.model.LoginModel;
import com.example.as.uestc.login.view.LoginView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by as on 2017/11/5.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginModel model=null;
    private LoginView view=null;
    private io.reactivex.Observer<Login> observer;
    public LoginPresenterImpl()
    {
        observer=new Observer<Login>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
            }

            @Override
            public void onNext(Login login) {
                if(login.getErrcode()==0)
                    ((LoginActivity)getView()).finishLogin(login);
                else
                    ((LoginActivity)getView()).showToast(login);
                Log.d("", "onNext:======================== "+login.getToken());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("", "onError: =============");
            }

            @Override
            public void onComplete() {
                disposable.dispose();
            }
        };
    }

    public void attach(LoginView view,LoginModel model)
    {
        this.view=view;
        this.model=model;
    }

    public LoginView getView()
    {
        return this.view;
    }

    public LoginModel getModel()
    {
        return this.model;
    }
    @Override
    public void login(PostUser user) {
        Log.d("", "onError: =============");

        getModel().getLogin(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.observer);
    }

    /**
     * 用来测试的方法
     * @param user
     * @param textView
     */
    public void loginV(PostUser user, final TextView textView)
    {
        observer=new Observer<Login>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
            }

            @Override
            public void onNext(Login login) {
                //((LoginActivity)getView()).saveLogin(login);
                textView.setText(login.getToken());
                Log.d("", "onNext:======================== "+login.getToken());
            }

            @Override
            public void onError(Throwable e) {
                Log.d("", "onError: =============");
            }

            @Override
            public void onComplete() {

            }
        };

        getModel().getLogin(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this.observer);
    }
}
