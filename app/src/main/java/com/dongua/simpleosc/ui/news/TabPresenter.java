package com.dongua.simpleosc.ui.news;

import com.dongua.simpleosc.base.BaseView;
import com.dongua.simpleosc.bean.News;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by duoyi on 17-12-8.
 */

public class TabPresenter implements NewsContract.Presenter,NewsContract.OnRequestListener<List<News>> {
    private NewsContract.View mView;
    private NewsContract.Model mModel;


    TabPresenter() {
        mModel = new TabModel();
        mModel.setRequestListener(this);
    }

    @Override
    public void requestAllNews() {
        mModel.getNews(null);
    }


    @Override
    public void successed(List<News> data) {
        mView.requestFinished(data);
    }

    @Override
    public void failed() {
        mView.requestFailed();
    }

    @Override
    public void requestNewsBefore(String pubDate) {
        mModel.getNews(pubDate);
    }

    @Override
    public void detach() {
        mView = null;

    }

    @Override
    public void attach(NewsContract.View view) {
        mView =  view;

    }

    @Override
    public void cancelRequest() {
        mModel.cancelRequest();
    }
}
