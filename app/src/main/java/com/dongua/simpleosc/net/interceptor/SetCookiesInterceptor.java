package com.dongua.simpleosc.net.interceptor;

import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.dongua.simpleosc.utils.SharedPreferenceUtil.PREF_COOKIE;

/**
 * Created by duoyi on 17-11-24.
 */

public class SetCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> cookies = (HashSet<String>) SharedPreferenceUtil.getSharedPreferences(PREF_COOKIE,new HashSet());
        if(!cookies.isEmpty()){
            for(String cookie:cookies)
            builder.addHeader("Cookie",cookie);
            Logger.d("addCookie:"+cookies);
        }

        return chain.proceed(builder.build());
    }
}
