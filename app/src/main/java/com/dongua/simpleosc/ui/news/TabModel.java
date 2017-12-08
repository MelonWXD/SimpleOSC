package com.dongua.simpleosc.ui.news;

/**
 * Created by duoyi on 17-12-8.
 */

public class TabModel implements NewsContract.Model {

    private NewsContract.OnRequestListener mListener;

    @Override
    public void getNews() {

    }


    @Override
    public void cacheData() {

    }

    @Override
    public void setRequestListener(NewsContract.OnRequestListener listener) {
        mListener = listener;
    }


}
