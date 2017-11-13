package com.example.as.uestc.Answer;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by as on 2017/11/12.
 */

public class MyTextWatcher implements TextWatcher {


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (s.toString().startsWith("."))
        {
            s.subSequence(0,0);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String content=s.toString();
        if(content==".")
        {
            s.delete(0,content.length()-1);
        }
        if(content.contains("."))
        {

        }
    }
}
