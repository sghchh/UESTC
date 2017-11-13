package com.example.as.uestc.Answer.networks;

import com.example.as.uestc.Answer.beans.ClassList;
import com.example.as.uestc.Answer.beans.CurrentClass;
import com.example.as.uestc.Answer.beans.ScoreRes;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by as on 2017/11/5.
 */
 interface NetWorkService {
    @GET("main/classList/")
    Observable<ClassList> getCurrentClass();

    @GET("main/classInfo/{id}/")
    Observable<CurrentClass> getCurrent(@Path("id")String classID);

    @POST("main/voteTeacher/")
    @FormUrlEncoded
    Observable<ScoreRes> getPostRes(@Field("data")String data);
}
