package com.manibala.framework.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static boolean isJsonString(String str) {
        try {
            new JSONObject(str);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

}
