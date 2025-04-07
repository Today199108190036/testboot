package com.test.testboot.utils;

import ch.qos.logback.classic.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.HashCode;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class JsonParserUtil {

    public static Logger logger= (Logger) LoggerFactory.getLogger(JsonParserUtil.class);
    /**
     * 解析 JSON 字符串并提取 type 属性
     *
     * @param jsonString 输入的 JSON 字符串
     * @return type 属性的值
     */
    public static String getTypeFromJson(String jsonString) {
        try {
            // 将 JSON 字符串解析为 JSONObject
            JSONObject jsonObject = JSON.parseObject(jsonString);

            // 返回 type 值
            return jsonObject.getString("type");
        } catch (Exception e) {
            // 处理解析异常
            logger.info("解析 JSON 字符串时发生错误: " + e.getMessage());
            return null;
        }
    }

    public static String getTableFromJson(String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return jsonObject.getString("table");
    }

    public static JSONArray getDataFromJson(String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        return jsonObject.getJSONArray("data");
    }

//    public static void main(String[] args) {
//        int i = 1;
//        System.out.println(HashCode.fromInt(i));
//        System.out.println(HashCode.fromInt(1));
//        System.out.println(HashCode.fromInt(2));
//        System.out.println(HashCode.fromLong(1L));
//        System.out.println(HashCode.fromString("6923531"));
//        Set set = new HashSet();
//    }
}
