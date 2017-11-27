package com.example.as.uestc.Answer.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.as.uestc.Answer.ZoomOutPageTransformer;
import com.example.as.uestc.Answer.beans.CurrentClass;
import com.example.as.uestc.Answer.beans.ScorePost;
import com.example.as.uestc.Answer.view.AnswerActivity;
import com.example.as.uestc.Answer.view.adapter.MyPagerAdapter;
import com.example.as.uestc.R;
import com.example.as.uestc.base.mvp.EventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by as on 2017/11/5.
 */

public class MainFragment extends Fragment {
    private String TOKEN,ID;
    private EventListener listener;
    private MainFragment context;
    private TextView back,classRank,rank,classer,details,push,description;
    private ViewPager viewPager;
    private List<View> views=new ArrayList<>();
    private CurrentClass currentClass;
    private int POSITION;

    private int STATE;
    public MainFragment()
    {
        super();
    }

    /*
    public MainFragment(EventListener listener,CurrentClass currentClass)
    {
        super();
        this.listener=listener;
        this.currentClass=currentClass;
        ID=currentClass.getClassID();
    }
    */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        TOKEN=getArguments().getString("token");
        //listener=(AnswerListenerImpl)getArguments().getSerializable("listener");
        //currentClass=(CurrentClass)getArguments().getSerializable("currentclass");
        ID=currentClass.getClassID();
        POSITION=getArguments().getInt("position");
        STATE=getArguments().getInt("state");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.answer_fragment,container,false);

        for(int i=0;i<5;i++)
        {
            View view1=inflater.inflate(R.layout.answer_fragment_pager_item,null,false);
            ImageView imageView=(ImageView) view1.findViewById(R.id.answer_fragment_pager_item_imageview);
            Glide.with(getActivity()).load(currentClass.getImages().get(i)).into(imageView);
            Log.d("", "onCreateView: "+currentClass.getImages().get(i));
            views.add(view1);
        }
        init(view);
        addListeners();
        return view;
    }

    private void init(View view)
    {
        //back=(TextView)view.findViewById(R.id.answer_fragment_back);
        classRank=(TextView)view.findViewById(R.id.answer_fragment_class_rank);
        classer=(TextView)view.findViewById(R.id.answer_fragment_class);
        rank=(TextView) view.findViewById(R.id.answer_fragment_rank);
        details=(TextView)view.findViewById(R.id.answer_fragment_details);
        //description=(TextView)view.findViewById(R.id.answer_fragment_description);
        push=(TextView)view.findViewById(R.id.answer_fragment_push);

        if(currentClass !=null)
        {
            classRank.setText(currentClass.getClassID());
            classer.setText(currentClass.getAcademy());
            rank.setText("答辩序号:"+currentClass.getOrderNum());
        }
        viewPager=(ViewPager)view.findViewById(R.id.answer_fragment_viewpager);
        MyPagerAdapter adapter=new MyPagerAdapter(views);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        ZoomOutPageTransformer transformation=new ZoomOutPageTransformer();
        viewPager.setPageTransformer(true,transformation);

        /*
        如果已经投过票了，就换成已投的图标，并且不可点击
         */
        if(STATE==1)
        {
            push.setBackground(getActivity().getDrawable(R.drawable.shape_has));
            push.setTextColor(Color.parseColor("#dddddddd"));
            push.setClickable(false);
        }
    }

    private void addListeners()
    {
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsDiaolg detailsDiaolg=new DetailsDiaolg();
                detailsDiaolg.show(getFragmentManager(),null);
            }
        });

        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PushDiaolg diaolg=new PushDiaolg();
                Bundle data=new Bundle();
                data.putString("classID",ID);
                data.putString("token",TOKEN);
                data.putSerializable("listener",listener);
                diaolg.setArguments(data);
                diaolg.setTargetFragment(context,0);
                diaolg.show(getFragmentManager(),null);
            }
        });
    }

    /**
     * “打分”按钮点击打分成功后，状态改变
     */
    public void resetDrawable()
    {
        push.setBackground(getActivity().getDrawable(R.drawable.shape_has));
        push.setClickable(false);
        push.setTextColor(Color.parseColor("#dddddddd"));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK)
        {
            //处理提交数据的事件
            String token=data.getExtras().getString("token");
            String id=data.getExtras().getString("classID");
            String score=data.getExtras().getString("score");
            ((AnswerActivity)getActivity()).postScore(new ScorePost(id,score,token),POSITION);
        }
    }

    public void setListener(EventListener listener)
    {
        this.listener=listener;
    }

    public void setCurrentClass(CurrentClass currentClass)
    {
        this.currentClass=currentClass;
    }
}
