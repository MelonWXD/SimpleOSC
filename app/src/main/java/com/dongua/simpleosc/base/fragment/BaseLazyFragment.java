package com.dongua.simpleosc.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Leiws on 17-12-29.
 */

public abstract class BaseLazyFragment extends BaseFragment {

    protected boolean isVisible;
    private boolean isPrepared;
    private boolean isFirst = true;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            lazyLoad();
        }else{
            isVisible = false;
            onInvisible();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if(getUserVisibleHint()){
            setUserVisibleHint(true);
        }
    }


    protected void lazyLoad(){
//        if(!isPrepared || !isVisible || !isFirst){
//            return;
//        }
        if(!isPrepared || !isVisible){
            return;
        }
        initWidget(mRoot);
        initData();

        isFirst = false;
    }


    protected void onInvisible() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRoot = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRoot);
        mRoot = initRootView(mRoot);

        return mRoot;
    }
}
