package com.dongua.simpleosc.ui.news;

import com.dongua.simpleosc.bean.SubBean;

import java.util.List;

/**
 * Created by duoyi on 17-12-20.
 */

public class BasePresenter<T> implements SubContract.Presenter,SubContract.OnRequestListener<T> {
    private SubContract.View mView;
    private SubContract.Model mModel;


    BasePresenter() {
        mModel = new BaseModel<T>();
        mModel.setRequestListener(this);
    }

//    protected abstract SubContract.Model getMyModel();


    @Override
    public void successed(List<T> bean) {
        mView.requestFinished(bean);
    }

    @Override
    public void failed() {
        mView.requestFailed();
    }


    @Override
    public void detach() {
        mView = null;

    }

    @Override
    public void attach(SubContract.View view) {
        mView = view;

    }

    @Override
    public void requestAll(int type) {
        mModel.getData(type,null);

    }

    @Override
    public void requestBefore(String pubDate, int type) {
        mModel.getData(type,pubDate);
    }

    @Override
    public void cancelRequest() {
        mModel.cancelRequest();
    }

    @Override
    public void loadMore() {

    }
}
