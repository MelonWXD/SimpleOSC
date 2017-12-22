package com.dongua.simpleosc.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dongua.simpleosc.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by duoyi on 17-11-17.
 */

public abstract class BaseToolBarFragment extends BaseFragment {

    private RelativeLayout mToolBarLayout;
    private Toolbar mToolBar;
    //根布局
    private LinearLayout mRootView = null;


    @Override
    protected View initRootView(View mRoot) {
        mRootView =new LinearLayout(getContext());
        mRootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mRootView.setOrientation(LinearLayout.VERTICAL);

        mToolBarLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.layout_toolbar,mRootView,false);
        mToolBar = mToolBarLayout.findViewById(R.id.toolbar);
        setCustomToolbar(mToolBar);

        mRootView.addView(mToolBarLayout);
        mRootView.addView(mRoot);
        return mRootView;
    }


    protected void setCustomToolbar(Toolbar toolbar){

    }

}
