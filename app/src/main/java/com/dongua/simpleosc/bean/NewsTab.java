package com.dongua.simpleosc.bean;

import java.io.Serializable;

/**
 * Created by duoyi on 17-11-27.
 */

public class NewsTab implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int TYPE_ALL = 0;
    public static final int TYPE_BLOG = 1;
    public static final int TYPE_NEWS = 4;
    public static final int TYPE_DAILY = 3;
    public static final int TYPE_POST = 2;

    private String name;
    private Boolean showBanner;
    private int type;
    private String herf;


    public NewsTab(String name, Boolean hasBanner, String herf) {
        this.name = name;
        this.showBanner = hasBanner;
        this.herf = herf;
    }

    public NewsTab(String name, int type, Boolean hasBanner, String herf) {
        this.name = name;
        this.type = type;
        this.showBanner = hasBanner;
        this.herf = herf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Boolean getShowBanner() {
        return showBanner;
    }

    public void setShowBanner(Boolean showBanner) {
        this.showBanner = showBanner;
    }

    public String getHerf() {
        return herf;
    }

    public void setHerf(String herf) {
        this.herf = herf;
    }
}
