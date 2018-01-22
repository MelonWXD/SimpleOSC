package com.dongua.simpleosc.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.fragment.BaseViewPagerFragment;
import com.dongua.simpleosc.bean.NewsTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by duoyi on 17-11-18.
 */

public class TweetFragment extends BaseViewPagerFragment {


    @BindView(R.id.tl_tweet_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_content)
    ViewPager mViewPager;

    private List<Integer> dataList;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tweet_tab;
    }

    @Override
    protected void initWidget(View root) {
        super.initWidget(root);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        dataList = new ArrayList<>();
        dataList.add(0);
        dataList.add(-1);
        dataList.add(123);
        mViewPager.setAdapter(new TweetPagerAdapter(getChildFragmentManager(), dataList));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    class TweetPagerAdapter extends FragmentPagerAdapter {

        private List<Integer> dataList;

        TweetPagerAdapter(FragmentManager fm, List<Integer> data) {
            super(fm);
            this.dataList = data;
        }

        @Override
        public Fragment getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }
    }
}
