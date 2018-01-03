package com.dongua.simpleosc.base.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.ui.news.SubFragment;

import java.util.List;

/**
 * Created by duoyi on 17-12-13.
 */

public abstract class BaseRecyclerAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>  implements View.OnClickListener,View.OnLongClickListener{

    protected Context mContext;
    protected List<T> mItem;

    public BaseRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(getItemLayoutID(), parent, false);
        return getViewHolder(root);
    }


//    @Override
//    public void onBindViewHolder(T holder, int position) {
//
//    }

    @Override
    public int getItemCount() {
        return 0;
    }


    protected abstract int getItemLayoutID();

    protected abstract T getViewHolder(View root);


    @Override
    public void onClick(View v) {
        onItemClick(v,(int)v.getTag());
    }

    @Override
    public boolean onLongClick(View v) {
       return onItemLongClick(v,(int)v.getTag());

    }

    protected abstract void onItemClick(View v, int tag);
    protected abstract boolean onItemLongClick(View v, int tag);

}
