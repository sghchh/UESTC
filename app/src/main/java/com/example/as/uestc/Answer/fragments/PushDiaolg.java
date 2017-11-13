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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.as.uestc.Answer.CircleImageView;
import com.example.as.uestc.Answer.MyTextWatcher;
import com.example.as.uestc.R;

/**
 * 打分的对话框
 * Created by as on 2017/11/5.
 */

public class PushDiaolg extends DialogFragment {
    private CircleImageView back;
    private TextView submit,title;
    private EditText edit;
    private PushDiaolg context;
    private String TOKEN,ID;

    public PushDiaolg()
    {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        TOKEN=getArguments().getString("token");
        ID=getArguments().getString("classID");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.answer_dialog_push,container,false);
        back=(CircleImageView)view.findViewById(R.id.answer_dialog_push_back);
        edit=(EditText)view.findViewById(R.id.answer_dialog_push_edit);
        edit.addTextChangedListener(new MyTextWatcher());
        title=(TextView)view.findViewById(R.id.answer_dialog_push_title);
        submit=(TextView)view.findViewById(R.id.answer_dialog_push_submit);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction=getActivity().getFragmentManager().beginTransaction();
                transaction.remove(context);
                transaction.commit();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit.getText().toString().equals("")||edit.getText()==null)
                {
                    Toast.makeText(getContext(),"请输入分数",Toast.LENGTH_SHORT).show();
                }
                else if(Float.parseFloat(edit.getText().toString())>100||Float.parseFloat(edit.getText().toString())<0)
                {
                    Toast.makeText(getContext(),"分值必须在0.0-100.0之间",Toast.LENGTH_SHORT).show();
                }
                else {
                    SureDialog dialog=new SureDialog();
                    Bundle bundle=new Bundle();
                    bundle.putString("score",edit.getText().toString());
                    bundle.putString("classID",ID);
                    bundle.putString("token",TOKEN);
                    dialog.setArguments(bundle);
                    dialog.setTargetFragment(context,0);
                    dialog.show(getFragmentManager(),null);

                    FragmentTransaction transaction=getActivity().getFragmentManager().beginTransaction();
                    transaction.remove(context);
                    transaction.commit();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK)
        {
            //提交数据的实现交给上一级Fragment来实现
            getTargetFragment().onActivityResult(0,Activity.RESULT_OK,data);
        }
    }
}
