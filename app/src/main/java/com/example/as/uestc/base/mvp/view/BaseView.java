package com.example.as.uestc.base.mvp.view;

import com.example.as.uestc.Answer.beans.ClassList;
import com.example.as.uestc.Answer.beans.CurrentClass;
import com.example.as.uestc.base.mvp.EventListener;

/**
 * Created by as on 2017/11/5.
 */

public interface BaseView extends MVPView {

    EventListener getListener();
    void setEventListener(EventListener eventListener);
    void initView(ClassList classList);
    void initFragment(CurrentClass currentClass,int position,int state);
}
