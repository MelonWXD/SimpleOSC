package com.dongua.simpleosc.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by duoyi on 17-11-25.
 */

public class Util {

    public static JsonObject string2Json(String jsonstr){
        JsonObject jsonData = new JsonParser().parse(jsonstr).getAsJsonObject();
        return jsonData;
    }


    public static String bean2Json(Object bean) {
        return new Gson().toJson(bean);
    }

    public static Object json2Bean(String json, Class beanClass) {
        Gson gson = new Gson();
        Object res = gson.fromJson(json, beanClass);
        return res;
    }

    public static JsonArray beanList2JsonArray(List<Object> beanList){
        JsonArray jsonArray = new Gson().toJsonTree(beanList).getAsJsonArray();
        return jsonArray;
    }


    public static List jsonArray2BeanList(String json, Type type){
//        type = new TypeToken<beanClass>().getType()
        return new Gson().fromJson(json,type);
    }
}
