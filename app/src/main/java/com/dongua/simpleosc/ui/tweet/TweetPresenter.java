package com.dongua.simpleosc.ui.tweet;

import com.dongua.simpleosc.bean.TweetBean;

import java.util.List;

import static com.dongua.simpleosc.ui.tweet.VpTweetFragment.TYPE_HOT;
import static com.dongua.simpleosc.ui.tweet.VpTweetFragment.TYPE_LATEST;
import static com.dongua.simpleosc.ui.tweet.VpTweetFragment.TYPE_MINE;

/**
 * author: Lewis
 * date: On 18-1-23.
 */

public class TweetPresenter implements TweetContract.Presenter, TweetContract.OnRequestListener {
    TweetContract.View mView;
    TweetContract.Model mModel;


    public TweetPresenter() {
        this.mModel = new TweetModel();
        mModel.setRequestListener(this);
    }

    @Override
    public void requestAll(int type) {
        mModel.getData(type, null);

    }

    @Override
    public void requestBefore(String pubDate, int type) {
        mModel.getData(type, pubDate);

    }

    @Override
    public void cancelRequest() {
        mModel.cancelRequest();
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void detach() {
        mView = null;
    }

    @Override
    public void attach(TweetContract.View view) {
        mView = view;
    }

    @Override
    public void successed(List<TweetBean> bean) {
        mView.requestFinished(bean);
    }

    @Override
    public void failed() {
        mView.requestFailed();
    }
}
