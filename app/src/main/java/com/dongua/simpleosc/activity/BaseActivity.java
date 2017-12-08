package com.dongua.simpleosc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.dongua.simpleosc.base.BasePresenter;
import com.dongua.simpleosc.base.BaseView;
import com.orhanobut.logger.Logger;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by duoyi on 17-11-15.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder mUnbinder;

//    protected BasePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());

        mUnbinder = ButterKnife.bind(this);

        setTranslucentStatus(true);

        init(savedInstanceState);
//        initPresenter();
        initView();
        initData();
    }

    protected void initPresenter(){
//        mPresenter = getPresenter();
    }
//
//    protected BasePresenter getPresenter() {
//        return null;
//    }

    protected void init(Bundle savedInstanceState) {

    }


    protected abstract int getLayoutID();

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Logger.d("onSaveInstanceState");
    }

    protected void initView() {

    }


    protected void initData() {

    }

//    @Override
//    public void setPresenter(Object presenter) {
//
//    }



    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}
