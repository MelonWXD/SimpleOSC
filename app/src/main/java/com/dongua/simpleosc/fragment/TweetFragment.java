package com.dongua.simpleosc.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dongua.simpleosc.App;
import com.dongua.simpleosc.R;
import com.dongua.simpleosc.base.fragment.BaseViewPagerFragment;
import com.dongua.simpleosc.bean.NewsTab;
import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.ui.tweet.VpTweetFragment;
import com.dongua.simpleosc.utils.Util;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by duoyi on 17-11-18.
 */

public class TweetFragment extends BaseViewPagerFragment {

    private String[] tabs = new String[]{"最新", "最热", "我的"};

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
//        dataList = new ArrayList<>();
//        dataList.add(0);
//        dataList.add(-1);
//        dataList.add(123);//userID
        mViewPager.setAdapter(new TweetPagerAdapter(getChildFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    class TweetPagerAdapter extends FragmentPagerAdapter {

        TweetPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return VpTweetFragment.newInstance(getContext(), VpTweetFragment.TYPE_LATEST);
                case 1:
                    return VpTweetFragment.newInstance(getContext(), VpTweetFragment.TYPE_HOT);
                case 2:
                    return VpTweetFragment.newInstance(getContext(), App.getCurUid());
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return tabs.length;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}
