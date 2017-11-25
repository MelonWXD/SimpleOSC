package com.dongua.simpleosc.activity;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;

import com.dongua.simpleosc.R;

/**
 * Created by duoyi on 17-11-25.
 */

public abstract class BaseToolBarActivity extends BaseActivity {



    private Toolbar mToolBar;
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
        mRootView.addView(mToolBar);
        mRootView.addView(mContentView);
    }

    /**
     * 初始化ToolBar
     */
    private void initToolBar() {
        mToolBar = (Toolbar) getLayoutInflater().inflate(R.layout.layout_toolbar,mRootView,false);
        setCustomToolbar(mToolBar);
    }

    /**
     * 子类自行扩展设置属性
     * @param toolbar
     */
    protected abstract void setCustomToolbar(Toolbar toolbar);

}
