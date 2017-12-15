package com.dongua.simpleosc.bean;

import android.os.Parcelable;

/**
 * 新闻Fragment的bean基类，提供公共方法来支持泛型，实现代码复用
 */

public interface INewsBean extends Parcelable{
    public String getPubDate();
    public long getPubDateLong();
}
