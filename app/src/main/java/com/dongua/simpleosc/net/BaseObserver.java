package com.dongua.simpleosc.net;


import com.dongua.simpleosc.utils.SharedPreferenceUtil;
import com.dongua.simpleosc.utils.Util;
import com.google.gson.JsonObject;
import com.orhanobut.logger.Logger;

import java.io.EOFException;

import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.dongua.simpleosc.utils.SharedPreferenceUtil.ACCESS_TOKEN;
import static com.dongua.simpleosc.utils.SharedPreferenceUtil.REFRESH_TOKEN;

/**
 * Created by duoyi on 17-12-18.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    //授权过期不传code 直接报EOF错误
    @Override
    public void onError(Throwable e) {

    }
}
