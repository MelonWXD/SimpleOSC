package com.dongua.simpleosc.ui.news;

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
import com.dongua.simpleosc.bean.News;
import com.dongua.simpleosc.bean.NewsTab;
import com.dongua.simpleosc.fragment.BaseRecyclerFragment;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.dongua.simpleosc.utils.Util.dateFormat;

/**
 * Created by duoyi on 17-11-27.
 */

public class TabFragment extends BaseRecyclerFragment implements NewsContract.View {
    public static final String TAB_NAME = "tab_name";
    public static final String TAB_HERF = "tab_herf";
    public static final String TAB_BANNER = "tab_banner";
    public static final String TAB_BEAN = "tab";

    public static final String LAST_UPDATE_NEWS = "update_news";

    public static final int MSG_REQUEST = 1;

    @BindView(R.id.rv_banner)
    Banner mBanner;
    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private NewsTab mTab;
    private TabRecyclerAdapter mAdapter;
    private List<News> mNewsDataList = new ArrayList<>();


    private NewsContract.Presenter mPresenter;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REQUEST:
                    mRefreshLayout.setRefreshing(false);
                    mAdapter.notifyDataSetChanged();
                    break;
                default:
                    ;

            }
        }
    };

    public static TabFragment newInstance(Context context, NewsTab tab) {
        TabFragment fragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAB_BEAN, tab);
//        bundle.putBoolean(TAB_BANNER, tab.getShowBanner());
//        bundle.putString(TAB_NAME, tab.getName());
//        bundle.putString(TAB_HERF, tab.getHerf());
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sub_tab;
    }


    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
//        String name = bundle.getString(TAB_NAME);
//        String herf = bundle.getString(TAB_HERF);
//        Boolean showBanner = bundle.getBoolean(TAB_BANNER);
//        mTab = new NewsTab(name,showBanner,herf);
        mTab = (NewsTab) bundle.getSerializable(TAB_BEAN);

        mPresenter = new TabPresenter(this);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        if (mTab != null && mTab.getShowBanner()) {
            mBanner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            });
            List<String> imgUrl = new ArrayList<>();
            imgUrl.add("haha");
            mBanner.setImages(imgUrl);
            mBanner.start();
        }
        mAdapter = new TabRecyclerAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setDistanceToTriggerSync(300);
//        mRefreshLayout.setSize(SwipeRefreshLayout.LARGE);
        mRefreshLayout.setColorSchemeResources(R.color.red200, R.color.yellow200,
                R.color.blue200, R.color.green200);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestData();
            }
        });
//        mRefreshLayout.setRefreshing(true);


    }

    @Override
    protected void initData() {
        searchDB();
        long lastUpdate = (long) SharedPreferenceUtil.get(LAST_UPDATE_NEWS, 0L);
        long nowTime = new Date().getTime();
//        Logger.d(lastUpdate);
//        Logger.d(nowTime);
        if (lastUpdate == 0 || nowTime - lastUpdate > 5 * 1000) {
            SharedPreferenceUtil.put(LAST_UPDATE_NEWS, nowTime);
            requestData();
        }
    }

    private void searchDB() {

        List<News> data = App.getDaoSession().getNewsDao().loadAll();
        if (data != null && !data.isEmpty()) {
            mNewsDataList.clear();
            mNewsDataList.addAll(data);
        }
    }


    //todo list->set 防止重复?
    private void requestData() {
        mRefreshLayout.setRefreshing(true);
        if (mNewsDataList.isEmpty()) {
            mPresenter.requestAllNews();
        } else {
            mPresenter.requestNewsBefore(mNewsDataList.get(0).getPubDate());
        }
    }

    @Override
    public void requestFinished(List<News> data) {
//        Logger.d(Thread.currentThread().getId());
//        mNewsDataList.clear();
        if (data != null) {
            mNewsDataList.addAll(0, data);

            Message message = Message.obtain();
            message.what = MSG_REQUEST;
            mHandler.sendMessage(message);
        }

    }


    class TabRecyclerAdapter extends RecyclerView.Adapter<TabRecyclerAdapter.NewsHolder> {


        @Override
        public TabRecyclerAdapter.NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(getActivity()).inflate(R.layout.layout_recycler_item, parent, false);
            return new NewsHolder(root);
        }

        @Override
        public void onBindViewHolder(NewsHolder holder, int position) {
            News data = mNewsDataList.get(position);
            holder.title.setText(data.getTitle());
//            holder.description = data.get();
            holder.time.setText(dateFormat(data.getPubDate()));
//            if (data.getCommentCount() > 0) {
//                holder.comment.setCompoundDrawables(getResources().getDrawable(R.mipmap.ic_comment), null, null, null);
//            }
            holder.comment.setText(String.valueOf(data.getCommentCount()));
        }

        @Override
        public int getItemCount() {
            return mNewsDataList.size();
        }


        class NewsHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView description;
            TextView time;
            TextView comment;

            NewsHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tv_title);
                description = itemView.findViewById(R.id.tv_description);
                time = itemView.findViewById(R.id.tv_time);
                comment = itemView.findViewById(R.id.tv_comment);
            }
        }
    }

}
