package com.zwj.zwjutils;


import com.alibaba.fastjson.JSON;

import java.util.List;

public class JsonUtil {

    public static <T> T getObject(String jsonString, Class<T> cls) {
        return JSON.parseObject(jsonString, cls);
    }

    public static <T> List<T> getObjects(String jsonString, Class<T> cls) {
        return JSON.parseArray(jsonString, cls);
    }


}
