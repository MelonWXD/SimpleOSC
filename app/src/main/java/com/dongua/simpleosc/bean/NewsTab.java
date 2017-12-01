package com.dongua.simpleosc.bean;

/**
 * Created by duoyi on 17-11-27.
 */

public class NewsTab {
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
