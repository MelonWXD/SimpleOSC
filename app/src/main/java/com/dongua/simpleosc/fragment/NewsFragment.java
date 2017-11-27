package com.dongua.simpleosc.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.adapter.ContentPagerAdapter;
import com.dongua.simpleosc.bean.NewsTab;
import com.dongua.simpleosc.ui.news.TabFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by duoyi on 17-11-18.
 */

public class NewsFragment extends BaseViewPagerFragment {

    private String[] tabs = new String[] {"开源资讯","推荐博客","技术问答","每日一搏"};


    @BindView(R.id.tl_news_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    private ContentPagerAdapter mAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_tab;
    }


    @Override
    protected void initWidget(View root) {
        super.initWidget(root);

        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText("t1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("t1"));
        mTabLayout.addTab(mTabLayout.newTab().setText("t1"));
        List<TabFragment> data = new ArrayList<>();
        TabFragment fragment = new TabFragment();
        fragment.setArguments(new Bundle());
        data.add(fragment);
        mAdapter = new ContentPagerAdapter(getChildFragmentManager(),data);
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public int getScroll() {
        return 200;
    }
}
