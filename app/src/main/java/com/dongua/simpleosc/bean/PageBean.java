package com.dongua.simpleosc.bean;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * 新闻Fragment的bean基类，提供公共方法来支持泛型，实现代码复用
 */

public abstract class PageBean<T> implements Serializable{
    private List<T> items;
}
