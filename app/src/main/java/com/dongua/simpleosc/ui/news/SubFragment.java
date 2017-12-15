package com.dongua.simpleosc.ui.news;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dongua.simpleosc.App;
import com.dongua.simpleosc.R;
import com.dongua.simpleosc.bean.NewsTab;
import com.dongua.simpleosc.bean.SubBean;
import com.dongua.simpleosc.base.fragment.BaseRecyclerFragment;
import com.dongua.simpleosc.db.SubBeanDao;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;

import java.util.Date;
import java.util.List;


import static com.dongua.simpleosc.utils.Util.dateFormat;

/**
 * Created by duoyi on 17-11-27.
 */

public class SubFragment extends BaseRecyclerFragment<SubBean> implements SubContract.View {

    private SubContract.Presenter mPresenter;
    public static final String LAST_UPDATE_SUBBEAN = "update_sub";

    @Override
    protected RecyclerView.Adapter getRecyclerAdapter() {
        return new SubTabRecyclerAdapter();
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
        mPresenter = new SubPresenter();
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
        loadFromDB();

        long lastUpdate = (long) SharedPreferenceUtil.get(LAST_UPDATE_SUBBEAN, 0L);
        long nowTime = new Date().getTime();
//        Logger.d(lastUpdate);
//        Logger.d(nowTime);
        if (!isRorate && (lastUpdate == 0 || nowTime - lastUpdate > 30 * 1000)) {
            Logger.d("request at initData() isRorate="+isRorate);
            SharedPreferenceUtil.put(LAST_UPDATE_SUBBEAN, nowTime);
            requestData();
        }
    }

//        @Override
//    protected void initData() {
//
//        initPresenter();
//        loadFromDB();
//        requestData();

//    }

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

    class SubTabRecyclerAdapter extends RecyclerView.Adapter<SubTabRecyclerAdapter.SubHolder> {


        @Override
        public SubHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(getActivity()).inflate(R.layout.layout_recycler_item, parent, false);
            return new SubHolder(root);
        }

        @Override
        public void onBindViewHolder(SubHolder holder, int position) {
            SubBean data = mDataList.get(position);
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


        class SubHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView description;
            TextView time;
            TextView comment;
            View line;

            SubHolder(View itemView) {
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
