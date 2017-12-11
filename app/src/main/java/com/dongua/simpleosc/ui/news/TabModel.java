package com.dongua.simpleosc.ui.news;

import com.dongua.simpleosc.bean.News;
import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.utils.Util;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by duoyi on 17-12-8.
 */

public class TabModel implements NewsContract.Model {

    private NewsContract.OnRequestListener<List<News>> mListener;
    private Disposable mNewsDisposable;
    @Override
    public void getNews(final String pubDate) {
        RetrofitClient.getInstance().getNewsList()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<News>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mNewsDisposable = d;
                    }

                    //todo cache
                    @Override
                    public void onNext(List<News> news) {
                        if(pubDate==null || pubDate.isEmpty()){
                            mListener.successed(news);
                        }else {
                            if(Util.dateCompare(news.get(0).getPubDate(),pubDate)){
                                mListener.successed(news);
                            }else {
                                mListener.successed(null);
                            }
                        }
//                        Logger.d(news);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.failed();
                    }

                    @Override
                    public void onComplete() {
//                        Logger.d("onComplete");

                    }
                });
    }



    @Override
    public void cacheData() {

    }

    @Override
    public void setRequestListener(NewsContract.OnRequestListener listener) {
        mListener = listener;
    }

    @Override
    public void cancelRequest() {
        if(!mNewsDisposable.isDisposed())
            mNewsDisposable.dispose();
    }
}
