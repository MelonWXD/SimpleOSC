package com.dongua.simpleosc.ui.news;

import android.content.Context;
import android.os.Bundle;
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
import com.orhanobut.logger.Logger;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by duoyi on 17-11-27.
 */

public class TabFragment extends BaseRecyclerFragment {
    public static final String TAB_NAME = "tab_name";
    public static final String TAB_HERF = "tab_herf";
    public static final String TAB_BANNER = "tab_banner";
    public static final String TAB_BEAN = "tab";

    @BindView(R.id.rv_banner)
    Banner mBanner;
    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;

    private NewsTab mTab;
    private TabRecyclerAdapter mAdapter;
    private List<News> dataList ;

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

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        searchDB();
        requestData();
    }

    private void searchDB() {
        dataList = App.getDaoSession().getNewsDao().loadAll();
        if(dataList == null)
            dataList = new ArrayList<>();
    }


    private void requestData() {
    }


    class TabRecyclerAdapter extends RecyclerView.Adapter<TabRecyclerAdapter.NewsHolder> {
        @Override
        public TabRecyclerAdapter.NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(getActivity()).inflate(R.layout.layout_recycler_item, parent, false);
            NewsHolder holder = new NewsHolder(root);
            return holder;
        }

        @Override
        public void onBindViewHolder(NewsHolder holder, int position) {
            News data = dataList.get(position);
            holder.title.setText(data.getTitle());
//            holder.description = data.get();
            holder.time.setText(data.getPubDate());
            holder.comment.setText(data.getCommentCount());
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }


        class NewsHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView description;
            TextView time;
            TextView comment;

            public NewsHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.tv_title);
                description = itemView.findViewById(R.id.tv_description);
                time = itemView.findViewById(R.id.tv_time);
                comment = itemView.findViewById(R.id.tv_comment);
            }
        }
    }

}
