package com.example.as.uestc.Answer.view.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.as.uestc.Answer.CircleImageView;
import com.example.as.uestc.Answer.beans.ClassList;
import com.example.as.uestc.Answer.beans.Info;
import com.example.as.uestc.R;

/**
 * Created by as on 2017/11/5.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements View.OnClickListener {
    private ClassList list;
    private Activity activity;

    private RecyclerClickListener listener;

    public MyAdapter(@NonNull ClassList list, Activity activity)
    {
        this.list=list;
        this.activity=activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setContent(list,position);
        //将position和state合封装成一个字符串
        holder.colloge.setTag(position+""+holder.state);
        holder.colloge.setOnClickListener(this);
        holder.classes.setTag(position+""+holder.state);
        holder.classes.setOnClickListener(this);
        //holder.rank.setTag(position+""+holder.state);
        //holder.rank.setOnClickListener(this);
        //holder.imageView.setTag(position+""+holder.state);
        //holder.imageView.setOnClickListener(this);
        holder.details.setTag(position+""+holder.state);
        holder.details.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return list.getInfo().size();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null)
        {
            /*
            将position和state从字符串中解析出来
             */
            String message=v.getTag().toString();
            int state=message.endsWith("0")?0:1;
            int position=Integer.parseInt(message.substring(0,message.length()-1));

            listener.recyclerClick(position,state);
        }
    }

    public ClassList getList()
    {
        return this.list;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private int state;
        private ImageView imageView;
        private TextView rank,classes,colloge;
        private Button details;
        //private TickView tickView;
        private CircleImageView logo;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.recycler_item_image);
            //rank=(TextView)itemView.findViewById(R.id.recycler_item_rank);
            classes=(TextView)itemView.findViewById(R.id.recycler_item_class);
            colloge=(TextView)itemView.findViewById(R.id.recycler_item_college);
            details=(Button) itemView.findViewById(R.id.recycler_item_detail);
            //tickView=(TickView)itemView.findViewById(R.id.recycler_item_tick);
            logo=(CircleImageView)itemView.findViewById(R.id.recycler_item_logo);
        }

        private void setContent(ClassList list,int position)
        {
            Info info=list.getInfo().get(position);
            state=info.getHavenVote();
            Glide.with(activity).load(info.getCover()).fitCenter().into(imageView);

            //rank.setText(info.getOrderNum());
            classes.setText(info.getClassID());
            colloge.setText(info.getAcademy());
            if(info.getHavenVote()==0)
                logo.setBackground(activity.getDrawable(R.drawable.not));
            else
                logo.setBackground(activity.getDrawable(R.drawable.has));
        }

        public CircleImageView getLogo()
        {
            return this.logo;
        }
    }


    public void setMyRecyclerListener(RecyclerClickListener listener)
    {
        this.listener=listener;
    }
    public interface RecyclerClickListener{
        void recyclerClick(int position,int state);
    }
}
