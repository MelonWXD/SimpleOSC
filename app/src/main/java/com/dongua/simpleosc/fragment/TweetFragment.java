package com.dongua.simpleosc.fragment;

import com.dongua.simpleosc.R;

/**
 * Created by duoyi on 17-11-18.
 */

public class TweetFragment extends BaseViewPagerFragment {
    @Override
    protected int getLayoutId() {
         return R.layout.fragment_tweet_tab;
    }

    @Override
    public int getScroll() {
        return 100;
    }
}
