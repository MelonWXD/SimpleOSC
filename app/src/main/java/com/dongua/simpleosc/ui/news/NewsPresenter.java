package com.dongua.simpleosc.ui.news;

import java.util.List;

/**
 * Created by Leiws on 17-12-20.
 */

public class NewsPresenter<T> implements NewsContract.Presenter,NewsContract.OnRequestListener<T> {
    private NewsContract.View mView;
    private NewsContract.Model mModel;


    NewsPresenter() {
        mModel = new NewsModel<T>();
        mModel.setRequestListener(this);
    }

//    protected abstract NewsContract.Model getMyModel();


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
    public void attach(NewsContract.View view) {
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
