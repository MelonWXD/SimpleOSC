package com.dongua.simpleosc.ui.news;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dongua.simpleosc.R;
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
    public static final String BEAN_TAB = "tab";

    @BindView(R.id.rv_banner)
    Banner mBanner;
    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;

    private NewsTab mTab;

    public static TabFragment newInstance(Context context, NewsTab tab) {
        TabFragment fragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(TAB_BANNER, tab.getShowBanner());
        bundle.putString(TAB_NAME, tab.getName());
        bundle.putString(TAB_HERF, tab.getHerf());
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
        String name = bundle.getString(TAB_NAME);
        String herf = bundle.getString(TAB_HERF);
        Boolean showBanner = bundle.getBoolean(TAB_BANNER);
        mTab = new NewsTab(name,showBanner,herf);
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        if (mTab != null && mTab.getShowBanner()) {
            mBanner.setImageLoader(new GlideImageLoader());
            List<String> imgUrl = new ArrayList<>();
            imgUrl.add("haha");
            mBanner.setImages(imgUrl);
            mBanner.start();
        }
    }

    public CharSequence getName() {
        return mTab.getName();
    }

    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */


            //Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
            Logger.d("path=" + path);


        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建

    }

}
