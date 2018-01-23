package com.dongua.simpleosc.ui.tweet;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dongua.simpleosc.App;
import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.adapter.BaseRecyclerAdapter;
import com.dongua.simpleosc.base.fragment.BaseRecyclerFragment;
import com.dongua.simpleosc.bean.TweetBean;
import com.dongua.simpleosc.db.TweetBeanDao;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;

import java.util.Date;
import java.util.List;

import static com.dongua.simpleosc.utils.Util.dateFormat;

/**
 * author: Lewis
 * data: On 18-1-22.
 */

public class VpTweetFragment extends BaseRecyclerFragment<TweetBean> implements TweetContract.View {

    public static final String BUNDLE_TWEET_FLAG = "tweet_flag";
    public static final String LAST_UPDATE_TWEET = "update_tweet";
    public static final int TYPE_LATEST = 0;
    public static final int TYPE_HOT = -1;
    public static final int TYPE_MINE = 3;
    private int mTweetType;

    private TweetPresenter mPresenter;

    @Override
    protected RecyclerView.Adapter getRecyclerAdapter() {
        return new TweetRecyclerAdapter(getContext());
    }


    public static VpTweetFragment newInstance(Context context, int type) {

        VpTweetFragment fragment = new VpTweetFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_TWEET_FLAG, type);
        fragment.setArguments(bundle);
        return fragment;

    }


    @Override
    protected void initArguments(Bundle bundle) {
        super.initArguments(bundle);
        mTweetType = bundle.getInt(BUNDLE_TWEET_FLAG, TYPE_LATEST);
    }

    protected void initPresenter() {
        mPresenter = new TweetPresenter();
        mPresenter.attach(this);
    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        initPresenter();
    }

    @Override
    protected void initData() {
//        super.initData();
        loadFromDB();

        //todo 把请求时间改为变量
        long lastUpdate = (long) SharedPreferenceUtil.get(LAST_UPDATE_TWEET+mTweetType, 0L);
        long nowTime = new Date().getTime();
        if (!isRorate && (lastUpdate == 0 || nowTime - lastUpdate > 30 * 1000)) {
            SharedPreferenceUtil.put(LAST_UPDATE_TWEET+mTweetType, nowTime);
            requestData();
        }
    }

    @Override
    protected void requestData() {
        mRefreshLayout.setRefreshing(true);

        if (mDataList.isEmpty()) {
            mPresenter.requestAll(mTweetType);

        } else {
            mPresenter.requestBefore(mDataList.get(0).getPubDate(), mTweetType);
        }
    }

    @Override
    protected void loadFromDB() {
        if (mDataList.isEmpty()) {
            List data = App.getDaoSession().getTweetBeanDao().queryBuilder()
                    .where(TweetBeanDao.Properties.Type.eq(mTweetType))
                    .orderDesc(TweetBeanDao.Properties.PubDateLong)
                    .limit(15)
                    .list();

            if (data != null && !data.isEmpty()) {
                mDataList.clear();
                mDataList.addAll(data);
            }
        }
    }

    @Override
    protected void loadMoreFromDB() {
        long minTime = mDataList.get(mDataList.size() - 1).getPubDateLong();


        List<TweetBean> data = App.getDaoSession().getTweetBeanDao().queryBuilder()
                .orderDesc(TweetBeanDao.Properties.PubDateLong)
                .where(TweetBeanDao.Properties.PubDateLong.lt(minTime))
                .where(TweetBeanDao.Properties.Type.eq(mTweetType))
                .limit(15)
                .list();

        if (data != null && !data.isEmpty()) {
            mDataList.addAll(data);
            sendMsgLoadMoreSuccess();
        } else {
            sendMsgLoadMoreFail();
        }
    }

    @Override
    public void requestFinished(List<TweetBean> data) {
        if (data != null && !data.isEmpty()) {
            mDataList.addAll(0, data);
            sendMsgRequestSuccess();

        } else {
            sendMsgRequestNoUpdate();
        }
    }

    @Override
    public void requestFailed() {
        sendMsgRequestFail();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }


    class TweetRecyclerAdapter extends BaseRecyclerAdapter<TweetRecyclerAdapter.TweetHolder> {


        public TweetRecyclerAdapter(Context mContext) {
            super(mContext);
        }


        @Override
        protected int getItemLayoutID() {
            return R.layout.layout_recycler_item_tweet;
        }

        @Override
        protected TweetRecyclerAdapter.TweetHolder getViewHolder(View root) {
            return new TweetHolder(root);
        }

        @Override
        public void onBindViewHolder(TweetRecyclerAdapter.TweetHolder holder, int position) {
            holder.itemView.setTag(position);

            TweetBean tb = mDataList.get(position);
            holder.author.setText(tb.getAuthor());
            holder.body.setText(tb.getBody());
            holder.time.setText(dateFormat(tb.getPubDate()));
            holder.comment.setText(String.valueOf(tb.getCommentCount()));
            Glide.with(getContext())
                    .load(tb.getPortrait())
                    .into(holder.portrait);

            if (tb.getImgSmall() != null && !tb.getImgSmall().isEmpty()) {
                holder.smallImg.setVisibility(View.VISIBLE);
                Glide.with(getContext())
                        .load(tb.getImgSmall())
                        .into(holder.smallImg);
            }


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
            ImageView smallImg;

            TweetHolder(View itemView) {
                super(itemView);
                portrait = itemView.findViewById(R.id.iv_tweet_face);
                author = itemView.findViewById(R.id.tv_author_name);
                body = itemView.findViewById(R.id.tv_tweet_body);
                time = itemView.findViewById(R.id.tv_tweet_time);
                comment = itemView.findViewById(R.id.tv_tweet_comment_count);
                line = itemView.findViewById(R.id.h_line);
                smallImg = itemView.findViewById(R.id.iv_img_small);
            }
        }
    }
}
