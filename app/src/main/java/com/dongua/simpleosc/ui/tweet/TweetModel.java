package com.dongua.simpleosc.ui.tweet;

import android.database.sqlite.SQLiteConstraintException;

import com.dongua.simpleosc.App;
import com.dongua.simpleosc.bean.TweetBean;
import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.utils.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.dongua.simpleosc.ui.tweet.VpTweetFragment.TYPE_HOT;
import static com.dongua.simpleosc.ui.tweet.VpTweetFragment.TYPE_LATEST;
import static com.dongua.simpleosc.ui.tweet.VpTweetFragment.TYPE_MINE;
import static com.dongua.simpleosc.utils.Util.formatTweetContent;
import static com.dongua.simpleosc.utils.Util.str2Date;

/**
 * author: Lewis
 * data: On 18-1-23.
 */

public class TweetModel implements TweetContract.Model {
    private TweetContract.OnRequestListener mListener;
    private Disposable mDisposable;

    @Override
    public void cacheData(List<TweetBean> data, int type) {
        for (TweetBean bean : data) {
            try {
                App.getDaoSession().getTweetBeanDao().save(bean);
            } catch (SQLiteConstraintException exception) {
                Logger.e(exception.getMessage());
            }
        }
    }

    @Override
    public void setRequestListener(TweetContract.OnRequestListener listener) {
        mListener = listener;
    }

    @Override
    public void cancelRequest() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
    }

    @Override
    public void getData(final int type, final String pubDate) {
        RetrofitClient.getInstance().getTweetList(type)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            List<TweetBean> data = parseData(responseBody.string(), type);

                            if (pubDate != null && !pubDate.isEmpty()) {

                                Iterator<TweetBean> iterator = data.iterator();
                                while (iterator.hasNext()) {
                                    if (!Util.dateCompare(iterator.next().getPubDate(), pubDate))
                                        iterator.remove();
                                }
                            }

                            cacheData(data, type);
                            mListener.successed(data);


                        } catch (IOException e) {
                            e.printStackTrace();
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

    private List<TweetBean> parseData(String rep, final int type) {
        List<TweetBean> data = new ArrayList<>();
        JsonObject joo = Util.string2Json(rep);
        JsonArray ja = joo.get("tweetlist").getAsJsonArray();
        for (int i = 0; i < ja.size(); i++) {
            JsonObject jo = ja.get(i).getAsJsonObject();
            int id = jo.get("id").getAsInt();
            String pubDate = jo.get("pubDate").getAsString();
            String body = jo.get("body").getAsString();
            Logger.i("===========Format before"+body);
            body = formatTweetContent(body);
            Logger.i("===========Format after"+body);

            String author = jo.get("author").getAsString();
            int authorid = jo.get("authorid").getAsInt();
            int commentCount = jo.get("commentCount").getAsInt();
            String portrait = jo.get("portrait").getAsString();

            String imgSmall = null;
            String imgBig = null;
            JsonElement je = jo.get("imgSmall");
            if (je != null) {
                imgSmall = je.getAsString();
                imgBig = jo.get("imgBig").getAsString();
            }
            data.add(new TweetBean(id, author, pubDate, str2Date(pubDate), authorid, portrait, body, commentCount, imgSmall, imgBig, type));
        }

        return data;
    }


}
