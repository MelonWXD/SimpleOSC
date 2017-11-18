package com.dongua.simpleosc.fragment;

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

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected View mRoot;
    protected Bundle mBundle;//获取setArguments的值
    protected Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        mContext =context;
        super.onAttach(context);
        Logger.d("onAttach");
    }



    @Override
    public void onDetach() {
        mContext = null;
        super.onDetach();
        Logger.d("onDetach");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBundle = getArguments();
        initBundle(mBundle);
        Logger.d("onCreate");


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d("onSaveInstanceState");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mUnbinder!=null){
            mUnbinder.unbind();
        }
        Logger.d("onDestroy");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflater = inflater;
        mRoot = inflater.inflate(getLayoutId(),container,false);
        mUnbinder = ButterKnife.bind(this,mRoot);

        initWidget(mRoot);
        initData();



        Logger.d("onCreateView");

        return mRoot;
    }




    protected abstract int getLayoutId();

    protected void initBundle(Bundle bundle) {

    }

    protected void initWidget(View root) {


    }

    protected void initData() {


    }
}
