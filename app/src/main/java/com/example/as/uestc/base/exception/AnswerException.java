package com.example.as.uestc.base.exception;

/**
 * Created by as on 2017/11/14.
 */

public class AnswerException extends RuntimeException {

    private int code;
    public static final int HAVE_POST=101;
    public static final int NOT_EXSIT=102;
    public static final int NOT_BEGIN=103;

    private String message;

    public AnswerException (int code)
    {
        this.code=code;
        setMessage(code);
    }
    @Override
    public String getMessage() {
        return this.message;
    }

    private void setMessage(int code)
    {
        switch (code)
        {
            case HAVE_POST:
                message="已经打过分了，不能重复评分";
                break;
            case NOT_EXSIT:
                message="不存在该班级";
                break;
            case NOT_BEGIN:
                message="还未开始答辩";
                break;
        }
    }
}
