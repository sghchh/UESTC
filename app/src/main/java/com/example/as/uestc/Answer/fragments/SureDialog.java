package com.example.as.uestc.Answer.fragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.as.uestc.R;

/**
 * 确定打分的对话框
 * Created by as on 2017/11/5.
 */

public class SureDialog extends DialogFragment {

    private String score,TOKEN,ID;
    private SureDialog context;
    private TextView title;
    private Button posi,nega;

    public SureDialog()
    {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        score=getArguments().getString("score");
        TOKEN=getArguments().getString("token");
        ID=getArguments().getString("classID");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.answer_dialog_sure,container,false);
        title=(TextView)view.findViewById(R.id.answer_dialog_sure_title);
        title.setText("你确定要打"+score+"分吗？");
        posi=(Button)view.findViewById(R.id.answer_dialog_sure_positive);
        nega=(Button)view.findViewById(R.id.answer_dialog_sure_nagetive);

        nega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=getActivity().getFragmentManager().beginTransaction();
                transaction.remove(context);
                transaction.commit();
            }
        });

        posi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("score",score);
                bundle.putString("token",TOKEN);
                bundle.putString("classID",ID);
                Intent intent=new Intent();
                intent.putExtras(bundle);

                //点击确定后提交数据交给上一个fragment来完成
                getTargetFragment().onActivityResult(0, Activity.RESULT_OK,intent);
                FragmentTransaction transaction=getActivity().getFragmentManager().beginTransaction();
                transaction.remove(context);
                transaction.commit();
            }
        });
        return view;
    }
}
