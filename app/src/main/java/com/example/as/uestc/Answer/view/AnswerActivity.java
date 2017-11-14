package com.example.as.uestc.Answer.view;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.as.uestc.Answer.TickView.TickView;
import com.example.as.uestc.Answer.beans.ClassList;
import com.example.as.uestc.Answer.beans.CurrentClass;
import com.example.as.uestc.Answer.beans.ScorePost;
import com.example.as.uestc.Answer.fragments.MainFragment;
import com.example.as.uestc.Answer.model.AnswerModelImpl;
import com.example.as.uestc.Answer.presenter.AnswerListenerImpl;
import com.example.as.uestc.Answer.presenter.AnswerPreImpl;
import com.example.as.uestc.Answer.view.adapter.MyAdapter;
import com.example.as.uestc.R;

public class AnswerActivity extends AnswerView {

    public TextView test;
    private String TOKEN;
    private AnswerView answerView;
    private AnswerPreImpl pre;
    private RecyclerView recycler;
    private ImageView head;
    private MainFragment fragment;

    private MyAdapter.RecyclerClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        answerView=(AnswerView) this;
        TOKEN=getIntent().getStringExtra("token");
        setEventListener(new AnswerListenerImpl());
        pre=new AnswerPreImpl(answerView,new AnswerModelImpl());
        pre.loadInitialData();
    }

    @Override
    public void initView(final ClassList classList) {
        test=(TextView)findViewById(R.id.test);
        test.setText(TOKEN);
        head=(ImageView)findViewById(R.id.answer_activity_head);
        recycler=(RecyclerView)findViewById(R.id.answer_acitvity_recycler);
        LinearLayoutManager line=new LinearLayoutManager(this);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        final MyAdapter myAdapter=new MyAdapter(classList,this);
        recycler.setLayoutManager(line);
        recycler.setAdapter(myAdapter);

        listener=new MyAdapter.RecyclerClickListener() {
            @Override
            public void recyclerClick(int position) {
                getListener().callPresenterToRefreshFragment(classList.getInfo().get(position).getClassID());
            }
        };
        myAdapter.setMyRecyclerListener(listener);
    }


    @Override
    public void initFragment(CurrentClass currentClass) {

        Bundle bundle=new Bundle();
        bundle.putSerializable("listener",getListener());
        bundle.putSerializable("currentclass",currentClass);
        bundle.putString("token",TOKEN);
        fragment=new MainFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.answer_activity_fragment,fragment);
        transaction.commit();
    }


    /**
     * 处理Fragment传来的提交打分的事务
     * @param scorePost
     */
    public void postScore(ScorePost scorePost)
    {
        ((AnswerListenerImpl)getListener()).callPresenterToPostScore(scorePost);
    }

    /**
     * 通知TickView改变状态
     * 当打分成功的时候在AnswerPreImpl的postScore方法中被调用
     */
    public void notifyTickViewChange()
    {
        View view=recycler.getChildAt(0);
        ((TickView)view.findViewById(R.id.recycler_item_tick)).setChecked(true);
    }

    /**
     * 提交打分的返回数据的处理
     * 当打分失败的时候在AnswerPreImpl的postScore方法中被调用
     */
    public void showToast(String content)
    {
        Toast.makeText(this,content,Toast.LENGTH_LONG).show();
    }

}
