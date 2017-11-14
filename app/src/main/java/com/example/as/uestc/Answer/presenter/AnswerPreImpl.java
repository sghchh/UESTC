package com.example.as.uestc.Answer.presenter;

import android.util.Log;

import com.example.as.uestc.Answer.beans.ClassList;
import com.example.as.uestc.Answer.beans.CurrentClass;
import com.example.as.uestc.Answer.beans.ScorePost;
import com.example.as.uestc.Answer.beans.ScoreRes;
import com.example.as.uestc.Answer.model.AnswerModelImpl;
import com.example.as.uestc.Answer.view.AnswerActivity;
import com.example.as.uestc.base.mvp.model.BaseModel;
import com.example.as.uestc.base.mvp.view.BaseView;

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
    public void loadInitialData()
    {
        observer=new Observer<ClassList>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
            }

            @Override
            public void onNext(ClassList classList) {
                Log.d("onNext", "onNext: +++++++++++++++"+(classList ==null));
                instance.getView().initView(classList);
                refreshFragment(classList.getInfo().get(0).getClassID());
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
        ((AnswerModelImpl)instance.getModel()).getCurrentClass()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 1.根据当前的班级id进行网络数据请求
     * 2.得到数据后调用View的initFragment方法初始化fragment
     * @param classID 班级id
     */
    public void refreshFragment(String classID)
    {
        currentObserver=new Observer<CurrentClass>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
            }

            @Override
            public void onNext(CurrentClass currentClass) {
                getView().initFragment(currentClass);
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
    public void postScore(ScorePost scorePost)
    {
        postResObserver=new Observer<ScoreRes>() {
            Disposable disposable;
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;
            }

            @Override
            public void onNext(ScoreRes scoreRes) {
                ((AnswerActivity)getView()).test.setText(scoreRes.getErrcode());
                if(scoreRes.getErrcode()=="0")
                    ((AnswerActivity)getView()).notifyTickViewChange();
                else
                    ((AnswerActivity)getView()).showToast(scoreRes.getErrcode()+":"+scoreRes.getErrmsg());
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


