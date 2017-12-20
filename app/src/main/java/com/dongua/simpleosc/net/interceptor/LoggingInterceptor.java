package com.dongua.simpleosc.net.interceptor;

import com.dongua.simpleosc.net.RetrofitClient;
import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.dongua.simpleosc.utils.Util;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.dongua.simpleosc.utils.SharedPreferenceUtil.ACCESS_TOKEN;
import static com.dongua.simpleosc.utils.SharedPreferenceUtil.REFRESH_TOKEN;

/**
 * Created by duoyi on 17-11-24.
 */

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        Request request = chain.request();
        long t1 = System.nanoTime();//请求发起的时间

        String method = request.method();
        if ("POST".equals(method)) {
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
                Logger.d(String.format("发送请求 %s on %s %n%s %nRequestParams:{%s}",
                        request.url(), chain.connection(), request.headers(), sb.toString()));
            }
        } else {
            Logger.d(String.format("发送请求 %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
        }
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();//收到响应的时间
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理


        int retry = 3;
        while (!response.isSuccessful() && retry>0) {

            if(response.code() == 401 && response.message().equals("Unauthorized")){
                Logger.d("401:Unauthorized");
//                Logger.d("body"+response.peekBody(1024 * 1024).string());
//                String new_access = refreshToken();
//                if(!new_access.isEmpty()){
//
//                }
            }else if(response.code() == 400 && response.peekBody(1024 * 1024).string().contains("Invalid refresh token")){
                Logger.d("400:Invalid refresh token");
            }

            retry--;
        }
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Logger.d(
                String.format(Locale.getDefault(),"接收响应: [%s] %n返回json:[%s] %.1fms %n%s",
                        response.request().url(),
                        responseBody.string(),
                        (t2 - t1) / 1e6d,
                        response.headers()
                ));
        return response;
    }

    private String refreshToken() {

        final StringBuilder ret = new StringBuilder();
        RetrofitClient.getInstance().refreshToken()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        String resp = responseBody.string();
                        Logger.d(resp);
                        JsonObject jo = Util.string2Json(resp);
                        String a_token = jo.get("access_token").getAsString();
                        String r_token = jo.get("refresh_token").getAsString();
                        SharedPreferenceUtil.put(ACCESS_TOKEN, a_token);
                        SharedPreferenceUtil.put(REFRESH_TOKEN, r_token);
                        ret.append(a_token);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Logger.d("无法申请授权");
                    }
                });
        return ret.toString();
    }
}