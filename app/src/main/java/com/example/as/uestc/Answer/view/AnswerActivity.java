package com.example.as.uestc.Answer.view;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.as.uestc.Answer.CircleImageView;
import com.example.as.uestc.Answer.beans.ClassList;
import com.example.as.uestc.Answer.beans.CurrentClass;
import com.example.as.uestc.Answer.beans.ScorePost;
import com.example.as.uestc.Answer.fragments.MainFragment;
import com.example.as.uestc.Answer.model.AnswerModelImpl;
import com.example.as.uestc.Answer.presenter.AnswerListenerImpl;
import com.example.as.uestc.Answer.presenter.AnswerPreImpl;
import com.example.as.uestc.Answer.view.adapter.MyAdapter;
import com.example.as.uestc.R;
import com.example.as.uestc.login.LoginActivity;

public class AnswerActivity extends AnswerView {

    private DrawerLayout drawerLayout;
    public TextView username;
    private Button switchUser;
    private String TOKEN;
    private AnswerView answerView;
    private AnswerPreImpl pre;
    private RecyclerView recycler;
    private ImageView head;
    private MainFragment fragment;
    private SharedPreferences preferences;

    private MyAdapter.RecyclerClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        preferences=getPreferences(MODE_PRIVATE);
        answerView=(AnswerView) this;

        /*
        如果SharedPreferences中的token数据为空，说明是第一次登陆
        则启动loginActivity；如果不为空，就跳过loginActivity界面，直接启动主界面
         */
        TOKEN=preferences.getString("token",null);

        /*
        如果是第一次登陆，保存user信息到本地
         */
        if(getIntent().getStringExtra("token")!=null)
        {
            TOKEN=getIntent().getStringExtra("token");
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("username",getIntent().getStringExtra("username"));
            editor.putString("token",getIntent().getStringExtra("token"));
            editor.apply();
        }
        setEventListener(new AnswerListenerImpl());
        pre=new AnswerPreImpl(answerView,new AnswerModelImpl());

        if(TOKEN==null)
        {
            Intent intent=new Intent(this, LoginActivity.class);
            pre.loadInitialDataWithoutFragment();
            startActivity(intent);
        }
        else
            pre.loadInitialData(TOKEN);

    }

    @Override
    public void initView(final ClassList classList) {
        drawerLayout=(DrawerLayout)findViewById(R.id.answer_acitvity_drawer);

        username=(TextView)findViewById(R.id.answer_activity_username);
        switchUser=(Button)findViewById(R.id.answer_activity_switch);
        head=(ImageView)findViewById(R.id.answer_activity_head);

        username.setText(preferences.getString("username","未登录"));
        recycler=(RecyclerView)findViewById(R.id.answer_acitvity_recycler);
        LinearLayoutManager line=new LinearLayoutManager(this);
        line.setOrientation(LinearLayoutManager.VERTICAL);
        final MyAdapter myAdapter=new MyAdapter(classList,this);
        recycler.setLayoutManager(line);
        recycler.setAdapter(myAdapter);

        listener=new MyAdapter.RecyclerClickListener() {
            @Override
            public void recyclerClick(int position,int state) {
                getListener().callPresenterToRefreshFragment(classList.getInfo().get(position).getClassID(),position,classList.getInfo().get(position).getHavenVote());
            }
        };

        switchUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AnswerActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        myAdapter.setMyRecyclerListener(listener);

    }


    /**
     * 初始化Fragment的方法
     * @param currentClass Fragment中需要的数据，有网络访问得到
     * @param position 当前fragment显示的是班级列表中的哪一个班级的信息
     * @param state 当前Fragment对应班级列表的position位置的班级是否被打过分，0为否，1为是；如果为1的话，则Fragment中的“打分”按钮不可点
     */
    @Override
    public void initFragment(CurrentClass currentClass,int position,int state) {

        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        bundle.putSerializable("listener",getListener());
        bundle.putSerializable("currentclass",currentClass);
        bundle.putString("token",TOKEN);
        bundle.putInt("state",state);
        fragment=new MainFragment();
        fragment.setArguments(bundle);

        FragmentTransaction transaction=getFragmentManager().beginTransaction();
        transaction.replace(R.id.answer_activity_fragment,fragment);
        transaction.commit();
    }


    /**
     * 处理Fragment传来的提交打分的事务
     * @param scorePost 打分需要封装的数据
     * @param position 回传给Activity的Fragment所关联的RecyclerView中的哪一个班级
     */
    public void postScore(ScorePost scorePost,int position)
    {
        ((AnswerListenerImpl)getListener()).callPresenterToPostScore(scorePost,position);
    }

    /**
     * 通知TickView改变状态
     * 当打分成功的时候在AnswerPreImpl的postScore方法中被调用
     * @param position 改变RecyclerView中哪一个班级的图标的状态，由Fragment回传过来
     */
    public void notifyTickViewChange(int position)
    {
        fragment.resetDrawable();
        View view=recycler.getChildAt(position);
        ((CircleImageView)view.findViewById(R.id.recycler_item_logo)).setBackground(getDrawable(R.drawable.has));
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
