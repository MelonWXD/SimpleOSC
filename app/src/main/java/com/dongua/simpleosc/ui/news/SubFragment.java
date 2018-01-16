package com.dongua.simpleosc.ui.news;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dongua.simpleosc.App;
import com.dongua.simpleosc.R;
import com.dongua.simpleosc.activity.DetailActivity;
import com.dongua.simpleosc.base.adapter.BaseRecyclerAdapter;
import com.dongua.simpleosc.bean.NewsTab;
import com.dongua.simpleosc.bean.SubBean;
import com.dongua.simpleosc.base.fragment.BaseRecyclerFragment;
import com.dongua.simpleosc.db.PostBeanDao;
import com.dongua.simpleosc.db.SubBeanDao;
import com.dongua.simpleosc.listener.RecyclerItemListener;
import com.dongua.simpleosc.utils.ActivitySwitcher;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.dongua.simpleosc.activity.DetailActivity.HREF;
import static com.dongua.simpleosc.activity.DetailActivity.TYPE;
import static com.dongua.simpleosc.utils.Util.dateFormat;

/**
 * Created by duoyi on 17-11-27.
 */

public class SubFragment extends BaseRecyclerFragment<SubBean> implements NewsContract.View<SubBean> {

    private NewsContract.Presenter mPresenter;
    public static final String LAST_UPDATE_BLOG = "update_blog";
    public static final String LAST_UPDATE_NEWS = "update_news";


    private int mDataType ;

    @Override
    protected RecyclerView.Adapter getRecyclerAdapter() {
        BNRecyclerAdapter adapter = new BNRecyclerAdapter(getActivity());
        adapter.setItemListener(new RecyclerItemListener() {
            @Override
            public void onClick(View view, int pos) {

                HashMap<String,Object> argMap = new HashMap<>();
                argMap.put(HREF,mDataList.get(pos).getId());
                argMap.put(TYPE,mDataType);
                ActivitySwitcher.switchTo(getActivity(), DetailActivity.class,argMap);
            }

            @Override
            public boolean onLongClick(View view, int pos) {

                return true;
            }
        });
        return adapter;
    }

    public static SubFragment newInstance(Context context, NewsTab tab) {

        SubFragment fragment = new SubFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(BUNDLE_TAB_BEAN, tab);
        fragment.setArguments(bundle);
        return fragment;
//
//        if (tab.getType() == TYPE_BLOG) {
//            SubFragment<Blogs> fragment = new SubFragment();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(TAB_BEAN, tab);

//            fragment.setArguments(bundle);
//            return fragment;
//        }else {
//            SubFragment<News> fragment = new SubFragment();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(TAB_BEAN, tab);
//            fragment.setArguments(bundle);
//            return fragment;
//        }
    }

    protected void initPresenter() {
        mPresenter = new NewsPresenter<SubBean>();
        mPresenter.attach(this);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        initPresenter();
    }

    @Override
    protected void initData() {
        super.initData();
        mDataType = mTab.getType();//和postfragment一起放到父类去
        loadFromDB();
        if (mTab.getType() == NewsTab.TYPE_BLOG) {
            long lastUpdate = (long) SharedPreferenceUtil.get(LAST_UPDATE_BLOG, 0L);
            long nowTime = new Date().getTime();
            if (!isRorate && (lastUpdate == 0 || nowTime - lastUpdate > 30 * 1000)) {
                SharedPreferenceUtil.put(LAST_UPDATE_BLOG, nowTime);
                requestData();
            }
        } else if (mTab.getType() == NewsTab.TYPE_NEWS) {
            long lastUpdate = (long) SharedPreferenceUtil.get(LAST_UPDATE_NEWS, 0L);
            long nowTime = new Date().getTime();
            if (!isRorate && (lastUpdate == 0 || nowTime - lastUpdate > 30 * 1000)) {
                SharedPreferenceUtil.put(LAST_UPDATE_NEWS, nowTime);
                requestData();
            }
        } else {
            requestData();
        }

    }

    @Override
    protected void loadFromDB() {
        if (mDataList.isEmpty()) {
            List datas = App.getDaoSession().getSubBeanDao().queryBuilder()
                    .where(SubBeanDao.Properties.Type.eq(mTab.getType()))
                    .orderDesc(SubBeanDao.Properties.PubDateLong)
                    .limit(15)
                    .list();

            if (datas != null && !datas.isEmpty()) {
                mDataList.clear();
                mDataList.addAll(datas);
            }
        }
    }

    @Override
    protected void loadMoreFromDB() {
        Logger.d("loadMoreFromDB");
        long minTime = mDataList.get(mDataList.size() - 1).getPubDateLong();
        //todo time相同的要做处理

        List<SubBean> data = App.getDaoSession().getSubBeanDao().queryBuilder()
                .orderDesc(SubBeanDao.Properties.PubDateLong)
                .where(SubBeanDao.Properties.PubDateLong.lt(minTime))
                .where(SubBeanDao.Properties.Type.eq(mTab.getType()))
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
    protected void requestData() {
        mRefreshLayout.setRefreshing(true);

        if (mDataList.isEmpty()) {
            mPresenter.requestAll(mTab.getType());
        } else {
            mPresenter.requestBefore(mDataList.get(0).getPubDate(), mTab.getType());
        }
    }

    @Override
    public void requestFinished(List<SubBean> data) {
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
        mPresenter.cancelRequest();
    }

    class BNRecyclerAdapter extends BaseRecyclerAdapter<BNRecyclerAdapter.SubHolder> {


//        RecyclerItemListener itemListener;

        public BNRecyclerAdapter(Context mContext) {
            super(mContext);
        }

//
//        public void setItemListener(RecyclerItemListener itemListener) {
//            this.itemListener = itemListener;
//        }
//
//        @Override
//        public void onItemClick(View v, int postion) {
//            itemListener.onClick(v, postion);
//        }
//
//        @Override
//        public boolean onItemLongClick(View v, int postion) {
//            return itemListener.onLongClick(v, postion);
//        }

        @Override
        protected int getItemLayoutID() {
            return R.layout.layout_recycler_item_sub;
        }

        @Override
        protected SubHolder getViewHolder(View root) {
            return new SubHolder(root);
        }

        @Override
        public void onBindViewHolder(SubHolder holder, int position) {
            holder.itemView.setTag(position);

            SubBean data = mDataList.get(position);
            holder.title.setText(data.getTitle());
//            holder.description = data.get();
            String s = dateFormat(data.getPubDate());
            if (s.contains("小时") || s.contains("分钟")) {
                holder.today.setVisibility(View.VISIBLE);
            } else {
                holder.today.setVisibility(View.INVISIBLE);

            }
//            if(mTab.getType()==NewsTab.TYPE_BLOG){
//                holder.recommand.setVisibility(View.VISIBLE);
//            }
            holder.time.setText(String.format(getResources().getString(R.string.pub_info), data.getAuthor(), s));
            holder.comment.setText(String.valueOf(data.getCommentCount()));

        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }


        class SubHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView description;
            TextView time;
            TextView comment;
            View line;
            ImageView today;
            ImageView recommand;

            SubHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tv_title);
                description = itemView.findViewById(R.id.tv_description);
                time = itemView.findViewById(R.id.tv_author_time);
                comment = itemView.findViewById(R.id.tv_comment);
                line = itemView.findViewById(R.id.divider);
                today = itemView.findViewById(R.id.iv_today);
                recommand = itemView.findViewById(R.id.iv_recommand);
            }
        }
    }

}
