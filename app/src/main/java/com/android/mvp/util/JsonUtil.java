package com.android.mvp.util;



import com.android.mvp.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class JsonUtil {

    public static JSONObject mapToJSONObject(Map<String, ? extends Object> map) {
        if (map == null) {
            return null;
        }

        JSONObject jsonObj = new JSONObject();
        try {
            for (String key : map.keySet()) {
                jsonObj.put(key, map.get(key));
            }

        } catch (JSONException e) {
            LogUtil.e(Constants.LOG_TAG, e.getMessage(), e);
        }

        return jsonObj;
    }

    public static String mapToJSONString(Map<String, ? extends Object> map) {
        String jsonString = null;
        JSONObject jsonObj = mapToJSONObject(map);
        if (jsonObj != null) {
            jsonString = jsonObj.toString();
        }

        return jsonString;
    }

    public static boolean copyJSONObject(JSONObject dst, JSONObject src) {
        if (dst == null || src == null) {
            return false;
        }

        Iterator<String> keys = src.keys();
        if (keys == null) {
            return false;
        }

        try {
            while (keys.hasNext()) {
                String key = keys.next();
                dst.put(key, src.get(key));
            }
        } catch (Exception e) {
            LogUtil.e(Constants.LOG_TAG, e.getMessage(), e);
            return false;
        }
        return true;
    }
}
