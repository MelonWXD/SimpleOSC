package com.dongua.simpleosc.net.convert;


import com.dongua.simpleosc.bean.News;
import com.dongua.simpleosc.utils.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by dongua on 17-8-10.
 */

public class NewsConverter implements Converter<ResponseBody, List<News>> {

    private static NewsConverter INSTANCE = new NewsConverter();

    public static NewsConverter create() {
        return INSTANCE;
    }



    @Override
    public List<News> convert(ResponseBody responseBody) throws IOException {
        List<News> newsList = new ArrayList<>();
        String resp = responseBody.string();
        JsonObject source = Util.string2Json(resp);
        JsonArray newsArray = source.get("newslist").getAsJsonArray();
        String jsonarray = Util.jsonArray2String(newsArray);
        try {

            newsList = Util.jsonArray2BeanList(jsonarray,new TypeToken<List<News>>(){}.getType());
        }catch (Exception e){
            e.getMessage();
        }
//        Logger.d(resp);

        return newsList;
    }


}
