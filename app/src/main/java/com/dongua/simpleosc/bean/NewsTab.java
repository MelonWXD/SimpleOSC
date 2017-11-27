package com.dongua.simpleosc.bean;

/**
 * Created by duoyi on 17-11-27.
 */

public class NewsTab  {
    private String name;
    private int type;
    private String herf;

    public NewsTab(String name, int type, String herf) {
        this.name = name;
        this.type = type;
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

    public String getHerf() {
        return herf;
    }

    public void setHerf(String herf) {
        this.herf = herf;
    }
}
