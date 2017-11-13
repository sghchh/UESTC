package com.example.as.uestc.Answer.view.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.as.uestc.Answer.TickView.TickView;
import com.example.as.uestc.Answer.beans.ClassList;
import com.example.as.uestc.Answer.beans.Info;
import com.example.as.uestc.R;

/**
 * Created by as on 2017/11/5.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener {
    private ClassList list;
    private Activity activity;

    private RecyclerClickListener listener;

    public MyAdapter(@NonNull ClassList list, Activity activity)
    {
        this.list=list;
        this.activity=activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setContent(list,position);
        holder.colloge.setTag(position);
        holder.colloge.setOnClickListener(this);
        holder.classes.setTag(position);
        holder.classes.setOnClickListener(this);
        holder.rank.setTag(position);
        holder.rank.setOnClickListener(this);
        holder.imageView.setTag(position);
        holder.imageView.setOnClickListener(this);
        holder.details.setTag(position);
        holder.details.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return list.getInfo().size();
    }

    @Override
    public void onClick(View v) {
        if (listener!=null)
            listener.recyclerClick((int)v.getTag());
    }

    public ClassList getList()
    {
        return this.list;
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView rank,classes,colloge;
        private TextView details;
        private TickView tickView;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.recycler_item_image);
            rank=(TextView)itemView.findViewById(R.id.recycler_item_rank);
            classes=(TextView)itemView.findViewById(R.id.recycler_item_class);
            colloge=(TextView)itemView.findViewById(R.id.recycler_item_college);
            details=(TextView) itemView.findViewById(R.id.recycler_item_detail);
            tickView=(TickView)itemView.findViewById(R.id.recycler_item_tick);
        }

        private void setContent(ClassList list,int position)
        {
            Info info=list.getInfo().get(position);
            //Glide.with(activity).load(info.getCover()).into(imageView);
            rank.setText(info.getOrderNum());
            classes.setText(info.getClassID());
            colloge.setText(info.getAcademy());
        }
    }

    public void setMyRecyclerListener(RecyclerClickListener listener)
    {
        this.listener=listener;
    }
    public interface RecyclerClickListener{
        void recyclerClick(int position);
    }
}
