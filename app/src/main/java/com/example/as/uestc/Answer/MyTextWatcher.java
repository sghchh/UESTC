package com.example.as.uestc.Answer;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by as on 2017/11/12.
 */

public class MyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (s.length())
        {
            case 0:
                break;
            case 1:
                oneChar(s);
                break;
            case 2:
                twoChar(s);
                break;
            case 3:
                threeChar(s);
                break;
            case 4:
                fourChar(s);
                break;
            case 5:
                fiveChar(s);
                break;
            default:
                fiveChar(s.delete(5,s.length()-1));
                break;
        }
    }

    private void oneChar(Editable s)
    {
        if(s.toString().equals("."))
            s.delete(0,1);

    }

    private void twoChar(Editable s)
    {
        if(!s.toString().endsWith(".")&&s.toString().startsWith("0"))
            s.delete(0,1);
    }

    /*
    处理x.x   x..   xx.   xxx  三种情况
    其中需要处理的是x.. 和xxx二重情况
     */
    private void threeChar(Editable s)
    {
        if(s.toString().contains(".."))
            s.delete(2,3);
        if(!s.toString().contains("."))
        {
            s=Integer.parseInt(s.toString())>100?s.delete(2,3):s;
        }
    }

    /*
    处理100.  100x  xx..  xx.x  x.x.  x.xx四种情况
    需要处理的只有100x  xx..  x.x.  x.xx四种
     */
    private void fourChar(Editable s)
    {
        //100x
        if(s.toString().contains("100")&&!s.toString().endsWith("."))
            s.replace(s.length()-1,s.length(),".");
        //xx..
        if(s.toString().endsWith(".."))
            s.delete(s.length()-1,s.length());
        //x.x.
        if(s.toString().endsWith(".")&&!s.toString().endsWith("..")&&!s.toString().contains("100"))
            s.delete(s.length()-1,s.length());
        //x.xx
        if(s.subSequence(0,2).toString().endsWith(".")&&!s.toString().endsWith("."))
            s.delete(s.length()-1,s.length());
    }

    /*
    处理100.x  100..  xx.x.  xx.xx
     */
    private void fiveChar(Editable s)
    {
        if(s.toString().contains("100.")&&!s.toString().endsWith("0"))
            s.replace(s.length()-1,s.length(),"0");
        if(!s.toString().contains("100."))
            s.delete(s.length()-1,s.length());
    }
}
