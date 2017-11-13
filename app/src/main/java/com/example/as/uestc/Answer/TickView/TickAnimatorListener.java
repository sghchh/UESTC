package com.example.as.uestc.Answer.TickView;

/**
 * Created by as on 2017/11/4.
 */

public interface TickAnimatorListener {
    void onAnimationStart(TickView tickView);
    void onAnimationEnd(TickView tickView);

    abstract class TickAnimatorListenerAdapter implements TickAnimatorListener{
        @Override
        public void onAnimationStart(TickView tickView)
        {

        }

        @Override
        public void onAnimationEnd(TickView tickView) {

        }
    }
}
