package com.dongua.simpleosc.fragment;

import com.dongua.simpleosc.R;
import com.dongua.simpleosc.fragment.BaseFragment;

/**
 * Created by duoyi on 17-11-18.
 */

public class NewsFragment extends BaseRecyclerViewFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_tab;
    }


    @Override
    public int getScroll() {
        return 200;
    }
}
