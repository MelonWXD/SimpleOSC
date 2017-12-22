package com.dongua.simpleosc.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.dongua.simpleosc.utils.LogUtil;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by duoyi on 17-11-17.
 */

public abstract class BaseFragment extends Fragment {

    private LayoutInflater mInflater;
    protected Context mContext;
    protected View mRoot;
    protected Bundle mBundle;//获取setArguments的值
    protected Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }


    @Override
    public void onDetach() {
        mContext = null;
        super.onDetach();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        //mBundle非空判断是否要放在base?
        initArguments(mBundle);
        initBundle(savedInstanceState);


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveBundle(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        mRoot = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRoot);
        mRoot = initRootView(mRoot);
        initWidget(mRoot);
        initData();

        return mRoot;
    }

    protected View initRootView(View mRoot) {
        return mRoot;
    }


    protected abstract int getLayoutId();

    protected void initArguments(Bundle arguments) {
    }

    protected void initBundle(Bundle savedInstanceState) {

    }

    protected void saveBundle(Bundle outState) {

    }

    protected void initWidget(View root) {


    }

    protected void initData() {


    }
}
