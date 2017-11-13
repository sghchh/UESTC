package com.example.as.uestc.login.networks;

import com.example.as.uestc.login.bean.Login;
import com.example.as.uestc.login.bean.PostUser;
import com.google.gson.Gson;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by as on 2017/11/5.
 */

public class LoginManager {
    private static final String BASEURL="http://dev.jcuan.org/";
    private Retrofit RETROFIT=null;
    private static LoginManager instance=null;
    private LoginManager()
    {
        //OkHttpClient client=new OkHttpClient.Builder().connectTimeout(12000, TimeUnit.MILLISECONDS).build();
        RETROFIT=new Retrofit.Builder()
                .baseUrl(BASEURL)
                //.client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static LoginManager getInstance()
    {
        if (instance==null)
        {
            instance=new LoginManager();
        }
        return instance;
    }

    /**
     *
     * @return
     */
    public Observable<Login> login(PostUser user)
    {
        return RETROFIT.create(LoginService.class).login(new Gson().toJson(user));
    }
}
