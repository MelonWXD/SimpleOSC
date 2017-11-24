package com.dongua.simpleosc.net;

import com.dongua.simpleosc.net.interceptor.LoggingInterceptor;
import com.dongua.simpleosc.net.interceptor.SaveCookiesInterceptor;
import com.dongua.simpleosc.net.interceptor.SetCookiesInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import static com.dongua.simpleosc.net.ApiService.BASE_URL;


/**
 * Created by duoyi on 17-11-22.
 */

public class RetrofitClient {



    private static RetrofitClient mInstance;

    private static OkHttpClient mHttpClient;
    private static Retrofit mRetrofit;

    private ApiService mApi;


    public static RetrofitClient getInstance(){
        if(mInstance !=null){
            synchronized (RetrofitClient.class){
                if(mInstance == null){
                    mInstance = new RetrofitClient();
                }
            }
        }
        return mInstance;
    }


    private RetrofitClient(){

        mHttpClient = new OkHttpClient.Builder().build();
        mHttpClient.interceptors().add(new LoggingInterceptor());
        mHttpClient.interceptors().add(new SaveCookiesInterceptor());
        mHttpClient.interceptors().add(new SetCookiesInterceptor());
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mHttpClient)
                .build();

        mApi = mRetrofit.create(ApiService.class);
    }
}
