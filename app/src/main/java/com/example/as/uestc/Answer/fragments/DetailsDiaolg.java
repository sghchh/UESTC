package com.example.as.uestc.Answer.fragments;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.as.uestc.Answer.CircleImageView;
import com.example.as.uestc.R;

/**
 * 评分细则的对话展示框
 * Created by as on 2017/11/5.
 */

public class DetailsDiaolg extends DialogFragment {
    private DialogFragment context;
    private CircleImageView back;
    private TextView title,detail1,action,action1,action2,question,question1,question2,show,show1,show2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.answer_dialog_details,container,false);
        back=(CircleImageView)view.findViewById(R.id.answer_dialog_details_back);
        title=(TextView)view.findViewById(R.id.answer_dialog_details_title);
        detail1=(TextView)view.findViewById(R.id.answer_dialog_details_detail1);
        action=(TextView)view.findViewById(R.id.answer_dialog_details_action);
        action1=(TextView)view.findViewById(R.id.answer_dialog_details_action1);
        action2=(TextView)view.findViewById(R.id.answer_dialog_details_action2);
        question=(TextView)view.findViewById(R.id.answer_dialog_details_question);
        question1=(TextView)view.findViewById(R.id.answer_dialog_details_question1);
        question2=(TextView)view.findViewById(R.id.answer_dialog_details_question2);
        show=(TextView)view.findViewById(R.id.answer_dialog_details_show);
        show1=(TextView)view.findViewById(R.id.answer_dialog_details_show1);
        show2=(TextView)view.findViewById(R.id.answer_dialog_details_show2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=getActivity().getFragmentManager().beginTransaction();
                transaction.remove(context);
                transaction.commit();
            }
        });
        return view;
    }
}
