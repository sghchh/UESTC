package com.example.as.uestc.Answer.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.as.uestc.base.mvp.EventListener;
import com.example.as.uestc.base.mvp.view.BaseView;

/**
 * Created by as on 2017/11/5.
 */

public abstract class AnswerView extends AppCompatActivity implements BaseView {

    protected EventListener listener;

    @Override
    public EventListener getListener() {
        return listener;
    }

    @Override
    public void setEventListener(EventListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
