package com.dongua.simpleosc.ui.news;

import com.dongua.simpleosc.App;
import com.dongua.simpleosc.bean.News;
import com.dongua.simpleosc.db.NewsDao;
import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.utils.Util;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
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

public class TabModel implements NewsContract.Model<List<News>> {

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


                    @Override
                    public void onNext(List<News> news) {
                        if (pubDate == null || pubDate.isEmpty()) {
                            mListener.successed(news);
                            cacheData(news);
                        } else {
                            List<News> update = new ArrayList<>();
                            for (News n : news) {
                                if (Util.dateCompare(n.getPubDate(), pubDate)) {
                                    update.add(n);
                                }
                            }
                            mListener.successed(update);
                            cacheData(update);
                            Logger.d("新闻数据是否为空: " + update.isEmpty());

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
    public void cacheData(List<News> data) {
        NewsDao dao = App.getDaoSession().getNewsDao();
        for (News n : data) {
            n.setPubDateLong(Util.str2Date(n.getPubDate()));
            dao.save(n);
        }

    }

    @Override
    public void setRequestListener(NewsContract.OnRequestListener<List<News>> listener) {
        mListener = listener;
    }

    @Override
    public void cancelRequest() {
        if (!mNewsDisposable.isDisposed())
            mNewsDisposable.dispose();
    }


    @Override
    public void loadMore() {

    }
}
