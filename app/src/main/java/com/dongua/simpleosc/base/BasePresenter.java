package com.dongua.simpleosc.base;

/**
 * Created by Leiws on 17-11-15.
 */

public interface BasePresenter<V extends BaseView> {
    void detach();
    void attach(V view);


}
