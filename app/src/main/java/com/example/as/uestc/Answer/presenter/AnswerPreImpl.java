package com.example.as.uestc.Answer.presenter;

import android.util.Log;

import com.example.as.uestc.Answer.beans.ClassList;
import com.example.as.uestc.Answer.beans.CurrentClass;
import com.example.as.uestc.Answer.beans.Info;
import com.example.as.uestc.Answer.beans.ScorePost;
import com.example.as.uestc.Answer.beans.ScoreRes;
import com.example.as.uestc.Answer.beans.Token;
import com.example.as.uestc.Answer.model.AnswerModelImpl;
import com.example.as.uestc.Answer.view.AnswerActivity;
import com.example.as.uestc.base.mvp.model.BaseModel;
import com.example.as.uestc.base.mvp.view.BaseView;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by as on 2017/11/5.
 */

public class AnswerPreImpl extends AnswerPre {

    private AnswerPreImpl instance;
    private io.reactivex.Observer<ClassList> observer;
    private Observer<CurrentClass> currentObserver;
    private Observer<ScoreRes> postResObserver;

    public AnswerPreImpl(BaseView answerView, BaseModel answerModel)
    {
        attach(answerView,answerModel);
        instance=this;
    }

    /**
     * 1.从网络获取数据
     * 2.调用view的initView初始化界面的RecyclerView
     * 3.默认加载班级列表的第一个班级的数据来进行数据请求
     * 4.调用reflashFragment方法加载fragment来展示当前的班级
     */
    public void loadInitialData(String token)
    {
        observer=new Observer<ClassList>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
            }

            @Override
            public void onNext(ClassList classList) {
                instance.getView().initView(classList);
                //((AnswerActivity)instance.getView()).username.setText(classList.getInfo().get(1).getHavenVote());
                /*
                如果当前的token被删除了，或者在其他地方登陆
                则重新进入登录界面
                 */
                /*
                if(classList==null)
                {
                    ((AnswerActivity)getView()).showToast(classList.getErrcode()+":"+classList.getErrmsg());
                    ((AnswerActivity)getView()).reLogin();
                }
                else
                {
                    List<Info> info=classList.getInfo();
                    if(info!=null)
                        refreshFragment(info.get(0).getClassID(),0,info.get(0).getHavenVote());
                }
                */
                if(classList.getErrcode()==0)
                {
                    List<Info> info=classList.getInfo();
                    if(info!=null)
                        refreshFragment(info.get(0).getClassID(),0,info.get(0).getHavenVote());
                }
                else
                {
                    ((AnswerActivity)getView()).showToast(classList.getErrcode()+":"+classList.getErrmsg());
                    ((AnswerActivity)getView()).reLogin();
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d("", "onComplete: ++++++++++++++++++++++");
                disposable.dispose();
            }
        };
        ((AnswerModelImpl)instance.getModel()).getCurrentClass(new Gson().toJson(new Token(token)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 1.从网络获取数据
     * 2.调用view的initView初始化界面的RecyclerView
     * 3.默认加载班级列表的第一个班级的数据来进行数据请求
     */
    public void loadInitialDataWithoutFragment()
    {
        observer=new Observer<ClassList>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
            }

            @Override
            public void onNext(ClassList classList) {
                instance.getView().initView(classList);
                //refreshFragment(classList.getInfo().get(0).getClassID());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Log.d("", "onComplete: ++++++++++++++++++++++");
                disposable.dispose();
            }
        };
        ((AnswerModelImpl)instance.getModel()).getCurrentClass(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 1.根据当前的班级id进行网络数据请求
     * 2.得到数据后调用View的initFragment方法初始化fragment
     * @param classID 班级id
     * @param position 当前Fragment显示的是RecyclerView中的哪一个数据
     * @param state 当前班级是否已经打过分了，0未打，1已打
     */
    public void refreshFragment(String classID,final int position,final int state)
    {
        currentObserver=new Observer<CurrentClass>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
            }

            @Override
            public void onNext(CurrentClass currentClass) {
                getView().initFragment(currentClass,position,state);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                disposable.dispose();
            }
        };
        ((AnswerModelImpl)instance.getModel()).getCurrent(classID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(currentObserver);

    }

    /**
     * 老师打分的时候提交数据
     * @param scorePost
     */
    public void postScore(ScorePost scorePost, final int position)
    {
        postResObserver=new Observer<ScoreRes>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
            }

            @Override
            public void onNext(ScoreRes scoreRes) {
                //((AnswerActivity)getView()).test.setText(scoreRes.getErrcode());
                if(scoreRes.getErrcode()==0)
                    ((AnswerActivity)getView()).notifyTickViewChange(position);
                else
                    ((AnswerActivity)getView()).showToast(scoreRes.getErrcode()+":"+scoreRes.getErrmsg());
                //((AnswerActivity)getView()).notifyTickViewChange(position);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        ((AnswerModelImpl)instance.getModel()).getPostRes(scorePost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(postResObserver);
    }
}


