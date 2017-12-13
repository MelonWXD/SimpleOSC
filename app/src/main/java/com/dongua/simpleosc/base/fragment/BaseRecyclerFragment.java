package com.dongua.simpleosc.base.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.adapter.BaseRecyclerAdapter;
import com.dongua.simpleosc.bean.NewsTab;
import com.dongua.simpleosc.ui.LoadMoreView;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.dongua.simpleosc.utils.UIUtil;
import com.youth.banner.Banner;

import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.dongua.simpleosc.ui.news.TabFragment.LAST_UPDATE_NEWS;

/**
 * Created by duoyi on 17-11-27.
 */

public abstract class BaseRecyclerFragment<T> extends BaseFragment {

    public static final String TAB_BEAN = "tab";
    public static final int MSG_REQUEST_SUCCESS = 1;
    private static final int MSG_REQUEST_FAIL = 2;
    private static final int MSG_LOADMORE_SUCCESS = 3;
    private static final int MSG_LOADMORE_FAIL = 4;


    //    protected BaseRecyclerAdapter<T> mAdapter;
    protected RecyclerView.Adapter mAdapter;
    protected List<T> mDataList;
    protected NewsTab mTab;

    @BindView(R.id.rv_banner)
    Banner mBanner;
    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.cv_loadmore)
    LoadMoreView mLoadMore;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REQUEST_SUCCESS:
                    hideProgress();
                    mAdapter.notifyDataSetChanged();
                    break;
                case MSG_REQUEST_FAIL:
                    hideProgress();
                    UIUtil.showShortToast(getContext(), getString(R.string.request_fail));
                    break;
                case MSG_LOADMORE_SUCCESS:
                    hideProgress();
                    mAdapter.notifyDataSetChanged();
                    break;
                case MSG_LOADMORE_FAIL:
                    hideProgress();
                    break;
                default:
                    ;

            }
        }
    };

    private void hideProgress() {
        if (mRefreshLayout.isRefreshing())
            mRefreshLayout.setRefreshing(false);
        if (mLoadMore.getVisibility() != View.GONE)
            mLoadMore.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_base;
    }

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
        if (bundle != null) {
            mTab = (NewsTab) bundle.getSerializable(TAB_BEAN);
        }
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        mLoadMore.setDrawSpeed(LoadMoreView.FAST);

        mAdapter = getRecyclerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    com.orhanobut.logger.Logger.d("滑动到底部");
                    mLoadMore.setVisibility(View.VISIBLE);
                    loadMoreFromDB();
//                    mPresenter.loadMore();

                }
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
        loadFromDB();
        long lastUpdate = (long) SharedPreferenceUtil.get(LAST_UPDATE_NEWS, 0L);
        long nowTime = new Date().getTime();
//        Logger.d(lastUpdate);
//        Logger.d(nowTime);
        if (lastUpdate == 0 || nowTime - lastUpdate > 30 * 1000) {
            SharedPreferenceUtil.put(LAST_UPDATE_NEWS, nowTime);
            requestData();
        }
    }

    //    protected abstract BaseRecyclerAdapter<T> getRecyclerAdapter();
    protected abstract RecyclerView.Adapter getRecyclerAdapter();

    protected abstract void requestData();

    protected abstract void loadFromDB();

    protected abstract void loadMoreFromDB();

}
