package com.dongua.simpleosc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dongua.simpleosc.base.BaseView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by duoyi on 17-11-15.
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseView{

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());

        mUnbinder = ButterKnife.bind(this);


        init(savedInstanceState);
        initView();
        initData();
    }

    protected void init(Bundle savedInstanceState){

    }


    protected abstract int getLayoutID() ;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    protected void initView() {

    }


    protected void initData() {

    }

    @Override
    public void setPresenter(Object presenter) {

    }
}
