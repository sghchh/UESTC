package com.example.as.uestc.login.networks;

import com.example.as.uestc.login.bean.Login;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by as on 2017/11/5.
 */

public interface LoginService {
    @POST("main/loginTeacher/")
    @FormUrlEncoded
    io.reactivex.Observable<Login> login(@Field("data")String data);
}
