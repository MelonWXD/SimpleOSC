package com.dongua.simpleosc.net.convert;


import com.dongua.simpleosc.bean.SubBean;
import com.dongua.simpleosc.utils.Util;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by dongua on 17-8-10.
 */

public class SubBeanConverter implements Converter<ResponseBody, List<SubBean>> {

    private static SubBeanConverter INSTANCE = new SubBeanConverter();

    public static SubBeanConverter create() {
        return INSTANCE;
    }


    @Override
    public List<SubBean> convert(ResponseBody responseBody) throws IOException {
        List<SubBean> blogsList = new ArrayList<>();
        String resp = responseBody.string();
        JsonObject source = Util.string2Json(resp);

        try {
            JsonElement element = source.get("bloglist");
            if (element == null)
                element = source.get("newslist");

            JsonArray newsArray = element.getAsJsonArray();
            String jsonarray = Util.jsonArray2String(newsArray);
            blogsList = Util.jsonArray2BeanList(jsonarray, new TypeToken<List<SubBean>>() {}.getType());
        } catch (Exception e) {
            e.getMessage();
        }
//        Logger.d(resp);

        return blogsList;
    }


}
