package com.udacity.sanketbhat.bakingapp.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {

    public static <T> String serialize(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> List<T> deserializeList(String data, Type type) {
        return new Gson().fromJson(data, type);
    }
}
