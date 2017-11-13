package com.example.as.uestc.Answer.presenter;

import com.example.as.uestc.Answer.beans.ScorePost;
import com.example.as.uestc.Answer.view.AnswerActivity;
import com.example.as.uestc.Answer.view.AnswerView;

/**
 * 任何界面的改变（除了初始化）都需要经过该类作为桥梁
 * Created by as on 2017/11/5.
 */

public class AnswerListenerImpl extends AnswerListener {

    @Override
    public void notifyTickView(AnswerView answerView) {
        AnswerActivity activity=(AnswerActivity)answerView;
        activity.notifyTickViewChange();
    }

    public void postScore(ScorePost data)
    {
        ((AnswerPreImpl)getPresenter()).postScore(data);
    }

    @Override
    public void callPresenterToRefreshFragment(String classID) {
        ((AnswerPreImpl)getPresenter()).refreshFragment(classID);
    }
}
