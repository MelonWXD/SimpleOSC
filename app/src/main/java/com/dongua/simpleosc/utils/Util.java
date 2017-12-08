package com.dongua.simpleosc.utils;

import com.dongua.simpleosc.bean.News;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by duoyi on 17-11-25.
 */

public class Util {

    static Gson gson = new Gson();

    public static JsonObject string2Json(String jsonstr){
        JsonObject jsonData = new JsonParser().parse(jsonstr).getAsJsonObject();
        return jsonData;
    }


    public static String bean2Json(Object bean) {
        return new Gson().toJson(bean);
    }

    public static Object json2Bean(String json, Class beanClass) {

        Object res = gson.fromJson(json, beanClass);
        return res;
    }

    public static JsonArray beanList2JsonArray(List<Object> beanList){
        JsonArray jsonArray = gson.toJsonTree(beanList).getAsJsonArray();
        return jsonArray;
    }


    public static List jsonArray2BeanList(String jsonarray, Type type){
//        type = new TypeToken<beanClass>().getType()
        return gson.fromJson(jsonarray,type);

    }

    public static String jsonArray2String(JsonArray jsonarray){
//        type = new TypeToken<beanClass>().getType()
        return gson.toJson(jsonarray);
    }
}
