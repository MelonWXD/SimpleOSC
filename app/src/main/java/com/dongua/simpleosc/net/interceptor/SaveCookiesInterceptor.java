package com.dongua.simpleosc.net.interceptor;

import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.dongua.simpleosc.utils.SharedPreferenceUtil.PREF_COOKIE;

/**
 * Created by Leiws on 17-11-24.
 */

public class SaveCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (!response.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : response.headers("Set-Cookie")) {
                Logger.d("getCookie:"+header);
            }


        }
        return response;

    }
}
