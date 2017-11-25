package com.dongua.simpleosc.net;

import com.dongua.simpleosc.net.interceptor.LoggingInterceptor;
import com.dongua.simpleosc.net.interceptor.SaveCookiesInterceptor;
import com.dongua.simpleosc.net.interceptor.SetCookiesInterceptor;


import io.reactivex.Observable;
import okhttp3.OkHttpClient;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;




/**
 * Created by duoyi on 17-11-22.
 */

public class RetrofitClient {

    public static final String BASE_URL = "http://www.oschina.net";

    public static final String APP_ID = "ZQhp2vSqj0m17HfGyS0y";
    public static final String APP_KEY = "5lGVEzMexPFyLQiyN7P0af99yeplIvyj";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String DATA_TYPE = "json";
    public static final String REDIRECT_URL = "http://chun0dong-001-site1.site4future.com/";


    private static RetrofitClient mInstance;

    private static OkHttpClient mHttpClient;
    private static Retrofit mRetrofit;

    private ApiService mApi;


    public static RetrofitClient getInstance(){
        if(mInstance ==null){
            synchronized (RetrofitClient.class){
                if(mInstance == null){
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }


    private RetrofitClient(){

        mHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new SaveCookiesInterceptor())
                .addInterceptor(new SetCookiesInterceptor())
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mHttpClient)
                .build();

        mApi = mRetrofit.create(ApiService.class);
    }


    public Observable<ResponseBody> getToken(String code){
        return mApi.getToken(APP_ID,APP_KEY,GRANT_TYPE,REDIRECT_URL,code,DATA_TYPE);
    }
}
