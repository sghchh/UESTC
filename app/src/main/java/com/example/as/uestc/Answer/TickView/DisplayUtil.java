package com.example.as.uestc.Answer.TickView;

import android.content.Context;

/**
 * Created by as on 2017/11/4.
 */

public class DisplayUtil {
    public static int dp2px(Context context,float dp)
    {
        if(context==null)
            return (int)(dp*1.5f+0.5f);
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dp*scale+0.5f);
    }
}
