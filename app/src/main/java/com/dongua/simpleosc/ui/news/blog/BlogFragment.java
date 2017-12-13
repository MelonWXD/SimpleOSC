package com.dongua.simpleosc.ui.news.blog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongua.simpleosc.App;
import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.adapter.BaseRecyclerAdapter;
import com.dongua.simpleosc.bean.Blogs;
import com.dongua.simpleosc.bean.News;
import com.dongua.simpleosc.bean.NewsTab;
import com.dongua.simpleosc.db.NewsDao;
import com.dongua.simpleosc.base.fragment.BaseRecyclerFragment;
import com.dongua.simpleosc.ui.LoadMoreView;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.dongua.simpleosc.utils.UIUtil;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import com.dongua.simpleosc.ui.news.*;
import static com.dongua.simpleosc.utils.Util.dateFormat;

/**
 * Created by duoyi on 17-11-27.
 */

public class BlogFragment extends BaseRecyclerFragment<Blogs> {


    @Override
    protected RecyclerView.Adapter getRecyclerAdapter() {
        return new TabRecyclerAdapter();
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void loadFromDB() {

    }

    @Override
    protected void loadMoreFromDB() {

    }



    class TabRecyclerAdapter extends RecyclerView.Adapter<TabRecyclerAdapter.BlogsHolder> {


        @Override
        public BlogFragment.TabRecyclerAdapter.BlogsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(getActivity()).inflate(R.layout.layout_recycler_item, parent, false);
            return new BlogFragment.TabRecyclerAdapter.BlogsHolder(root);
        }

        @Override
        public void onBindViewHolder(TabRecyclerAdapter.BlogsHolder holder, int position) {
            Blogs data = mDataList.get(position);
            holder.title.setText(data.getTitle());
//            holder.description = data.get();
            holder.time.setText(String.format(getResources().getString(R.string.pub_info), data.getAuthor(), dateFormat(data.getPubDate())));
//            if (data.getCommentCount() > 0) {
//                holder.comment.setCompoundDrawables(getResources().getDrawable(R.mipmap.ic_comment), null, null, null);
//            }
            holder.comment.setText(String.valueOf(data.getCommentCount()));

        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }


        class BlogsHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView description;
            TextView time;
            TextView comment;
            View line;

            BlogsHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tv_title);
                description = itemView.findViewById(R.id.tv_description);
                time = itemView.findViewById(R.id.tv_time);
                comment = itemView.findViewById(R.id.tv_comment);
                line = itemView.findViewById(R.id.divider);
            }
        }
    }
}
