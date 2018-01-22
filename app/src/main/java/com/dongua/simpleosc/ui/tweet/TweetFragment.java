package com.dongua.simpleosc.ui.tweet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.adapter.BaseRecyclerAdapter;
import com.dongua.simpleosc.base.fragment.BaseRecyclerFragment;
import com.dongua.simpleosc.bean.SubBean;
import com.dongua.simpleosc.bean.TweetBean;
import com.dongua.simpleosc.ui.news.SubFragment;

import static com.dongua.simpleosc.utils.Util.dateFormat;

/**
 * author: Lewis
 * data: On 18-1-22.
 */

public class TweetFragment extends BaseRecyclerFragment<TweetBean> {
    @Override
    protected RecyclerView.Adapter getRecyclerAdapter() {
        return new TweetRecyclerAdapter(getContext());
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


    class TweetRecyclerAdapter extends BaseRecyclerAdapter<TweetFragment.TweetRecyclerAdapter.TweetHolder> {


        public TweetRecyclerAdapter(Context mContext) {
            super(mContext);
        }


        @Override
        protected int getItemLayoutID() {
            return R.layout.layout_recycler_item_tweet;
        }

        @Override
        protected TweetFragment.TweetRecyclerAdapter.TweetHolder getViewHolder(View root) {
            return null;
        }

        @Override
        public void onBindViewHolder(TweetFragment.TweetRecyclerAdapter.TweetHolder holder, int position) {
            holder.itemView.setTag(position);


        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }


        class TweetHolder extends RecyclerView.ViewHolder {

            TextView author;
            TextView body;
            TextView time;
            TextView comment;
            View line;
            ImageView portrait;

            TweetHolder(View itemView) {
                super(itemView);
                portrait = itemView.findViewById(R.id.iv_tweet_face);
                author = itemView.findViewById(R.id.tv_author_name);
                body = itemView.findViewById(R.id.tv_tweet_body);
                time = itemView.findViewById(R.id.tv_tweet_time);
                comment = itemView.findViewById(R.id.tv_tweet_comment_count);
                line = itemView.findViewById(R.id.h_line);
            }
        }
    }
}
