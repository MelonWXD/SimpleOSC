package com.dongua.simpleosc.base.fragment;

import android.support.v7.widget.Toolbar;

/**
 * Created by Leiws on 17-11-20.
 */

public abstract class BaseViewPagerFragment extends BaseToolBarFragment {

    public int getScroll() {
        return 0;
    }

    public int setScroll(int scroll) {
        return 0;
    }

    @Override
    protected void setCustomToolbar(Toolbar toolbar) {
        super.setCustomToolbar(toolbar);
    }
}
