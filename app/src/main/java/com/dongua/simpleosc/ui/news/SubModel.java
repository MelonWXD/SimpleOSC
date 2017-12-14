package com.dongua.simpleosc.ui.news;

import com.dongua.simpleosc.App;
import com.dongua.simpleosc.bean.SubBean;
import com.dongua.simpleosc.db.SubBeanDao;
import com.dongua.simpleosc.net.RetrofitClient;

import com.dongua.simpleosc.utils.Util;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.dongua.simpleosc.bean.NewsTab.TYPE_BLOG;
import static com.dongua.simpleosc.bean.NewsTab.TYPE_DAILY;
import static com.dongua.simpleosc.bean.NewsTab.TYPE_NEWS;

/**
 * Created by duoyi on 17-12-8.
 */

public class SubModel implements SubContract.Model {

    private SubContract.OnRequestListener mListener;
    private Disposable mDisposable;


    @Override
    public void cacheData(List<SubBean> data) {
        SubBeanDao dao = App.getDaoSession().getSubBeanDao();
        for (SubBean n : data) {
            n.setPubDateLong(Util.str2Date(n.getPubDate()));
            dao.save(n);
        }
    }


    @Override
    public void setRequestListener(SubContract.OnRequestListener listener) {
        mListener = listener;
    }


    @Override
    public void cancelRequest() {
        if (!mDisposable.isDisposed())
            mDisposable.dispose();
    }




    @Override
    public void getData(int type, String pubDate) {
        switch (type) {
            case TYPE_NEWS:
                requestNews(pubDate);
                break;
            case TYPE_BLOG:
                requestBlogs(pubDate);
                break;
            case TYPE_DAILY:
                break;
            default:
        }

    }

    private void requestNews(final String pubDate) {
        RetrofitClient.getInstance().getNewsList()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<SubBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }


                    @Override
                    public void onNext(List<SubBean> news) {
                        if (pubDate == null || pubDate.isEmpty()) {
                            mListener.successed(news);
                            cacheData(news);
                        } else {
                            List<SubBean> update = new ArrayList<>();
                            for (SubBean n : news) {
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

    private void requestBlogs(final String pubDate) {

        RetrofitClient.getInstance().getBlogsList()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<SubBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(List<SubBean> blogs) {
                        if (pubDate == null || pubDate.isEmpty()) {
                            mListener.successed(blogs);
                            cacheData(blogs);

                        } else {
                            List<SubBean> update = new ArrayList<>();
                            for (SubBean b : blogs) {
                                if (Util.dateCompare(b.getPubDate(), pubDate)) {
                                    update.add(b);
                                }
                            }
                            mListener.successed(update);
                            cacheData(update);
                            Logger.d("数据是否为空: " + update.isEmpty());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mListener.failed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
