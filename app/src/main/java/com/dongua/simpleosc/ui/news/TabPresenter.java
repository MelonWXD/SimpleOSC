package com.dongua.simpleosc.ui.news;

import com.dongua.simpleosc.base.BaseView;
import com.dongua.simpleosc.bean.News;

/**
 * Created by duoyi on 17-12-8.
 */

public class TabPresenter implements NewsContract.Presenter,NewsContract.OnRequestListener<News> {
    private NewsContract.View mView;
    private NewsContract.Model mModel;


    TabPresenter(NewsContract.View view) {
        mView = view;
        mModel = new TabModel();
        mModel.setRequestListener(this);
    }

    @Override
    public void requestAllNews() {
        mModel.getNews();
    }


    @Override
    public void successed(News data) {
        mView.updateRecyclerView(data);
    }
}
