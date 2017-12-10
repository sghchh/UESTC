package com.example.as.uestc.Answer.networks;

import com.example.as.uestc.Answer.beans.ClassList;
import com.example.as.uestc.Answer.beans.CurrentClass;
import com.example.as.uestc.Answer.beans.ScorePost;
import com.example.as.uestc.Answer.beans.ScoreRes;
import com.google.gson.Gson;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by as on 2017/11/5.
 */

public class RetrofitManager {
    private static final String BASEURL="http://youban.stuhome.com/";
    private Retrofit RETROFIT=null;
    private static RetrofitManager instance=null;
    private RetrofitManager()
    {
        //OkHttpClient client=new OkHttpClient.Builder().connectTimeout(12000, TimeUnit.MILLISECONDS).build();
        RETROFIT=new Retrofit.Builder()
                .baseUrl(BASEURL)
                //.client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static RetrofitManager getInstance()
    {
        if (instance==null)
        {
            instance=new RetrofitManager();
        }
        return instance;
    }

    /**
     * 得到当前班级的信息
     * @return
     */
    public Observable<ClassList> getCurrentClass(String token)
    {
        return RETROFIT.create(NetWorkService.class).getCurrentClass(token);
    }

    public Observable<CurrentClass> getCurrent(String classID)
    {
        return RETROFIT.create(NetWorkService.class).getCurrent(classID);
    }

    public Observable<ScoreRes> getPostRes(ScorePost data)
    {
        return RETROFIT.create(NetWorkService.class).getPostRes(new Gson().toJson(data));
    }

}
