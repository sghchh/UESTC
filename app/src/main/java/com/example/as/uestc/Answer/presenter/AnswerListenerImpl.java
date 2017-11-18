package com.example.as.uestc.Answer.presenter;

import com.example.as.uestc.Answer.beans.ScorePost;

/**
 * 任何界面的改变（除了初始化）都需要经过该类作为桥梁
 * Created by as on 2017/11/5.
 */

public class AnswerListenerImpl extends AnswerListener {

    public void callPresenterToPostScore(ScorePost data,int position)
    {
        ((AnswerPreImpl)getPresenter()).postScore(data,position);
    }

    @Override
    public void callPresenterToRefreshFragment(String classID,int position,int state) {
        ((AnswerPreImpl)getPresenter()).refreshFragment(classID,position,state);
    }
}
