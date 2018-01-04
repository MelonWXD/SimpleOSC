package com.dongua.simpleosc.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.dongua.simpleosc.R;

/**
 * Created by duoyi on 17-11-25.
 */

public abstract class BaseToolBarActivity extends BaseActivity {


    private LinearLayout mToolBarLayout;
    private RelativeLayout mToolBar;
    //根布局
    private LinearLayout mRootView = null;
    //子Activity原布局
    private View mContentView = null;


    /**
     * 根据子类传入的布局ID来获取View，在该View外面增加一个LL和ToolBar再setContentView
     * @param layoutResID 子类重写BaseActivity的getLayoutID
     */
    @Override
    public void setContentView(int layoutResID) {
        mContentView = LayoutInflater.from(this).inflate(layoutResID,null);
        initDectorView();
        super.setContentView(mRootView);
    }


    /**
     * 初始化根布局
     */
    private void initDectorView() {
        mRootView = new LinearLayout(this);
        mRootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mRootView.setOrientation(LinearLayout.VERTICAL);
        initToolBar();
        mRootView.addView(mToolBarLayout);
//        mRootView.addView(mToolBar);
        mRootView.addView(mContentView);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mToolBarLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_toolbar,mRootView,false);
        mToolBar = mToolBarLayout.findViewById(R.id.toolbar);
        setCustomToolbar(mToolBar);
    }

    /**
     * 子类自行扩展设置属性
     * @param toolbar
     */
    protected abstract void setCustomToolbar(View toolbar);

//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

}
