package com.example.as.uestc.base.mvp.presenter;

import com.example.as.uestc.base.mvp.model.BaseModel;
import com.example.as.uestc.base.mvp.view.BaseView;

/**
 * Created by as on 2017/11/5.
 */

public interface BasePresenter extends MVPPresenter {
    void attach(BaseView answerView, BaseModel answerModel);
    //List getInitDataFromModel();
    //void pushScores();
    //List getClassDetails();
    BaseView getView();
    BaseModel getModel();
}
