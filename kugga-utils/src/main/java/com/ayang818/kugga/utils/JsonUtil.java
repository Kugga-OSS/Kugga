package com.ayang818.kugga.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author 杨丰畅
 * @description TODO
 * @date 2020/1/13 14:24
 **/
public class JsonUtil {
    private static final Gson GSON = new GsonBuilder().create();

    public static String toJson(Object o) {
        return GSON.toJson(o);
    }

    public static <T> T fromJson(String jsonString, Class<T> klass) {
        return GSON.fromJson(jsonString, klass);
    }

    public static Gson getGson() {
        return GSON;
    }

    public static void main(String[] args) {
        System.out.println("hello");
    }
}
