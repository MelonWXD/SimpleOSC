package com.dongua.simpleosc.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.activity.BaseToolBarActivity;
import com.dongua.simpleosc.activity.MainActivity;
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
        List<NewsTab> data = new ArrayList<>();
        NewsTab tab1 = new NewsTab("开源资讯",true,"");
        NewsTab tab2 = new NewsTab("推荐博客",false,"");
        NewsTab tab3 = new NewsTab("技术问答",false,"");
        NewsTab tab4 = new NewsTab("每日一搏",false,"");
        data.add(tab1);
        data.add(tab2);
        data.add(tab3);
        data.add(tab4);
        mAdapter = new ContentPagerAdapter(getChildFragmentManager(),data);
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public int getScroll() {
        return 200;
    }



    public class ContentPagerAdapter extends FragmentPagerAdapter {

        private List<NewsTab> tabList;

        ContentPagerAdapter(FragmentManager fm, List<NewsTab> data) {
            super(fm);
            tabList = data;
        }

        @Override
        public Fragment getItem(int position) {
            return TabFragment.newInstance(getContext(), tabList.get(position));
        }

        @Override
        public int getCount() {
            return tabList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position).getName();
        }
    }
}
