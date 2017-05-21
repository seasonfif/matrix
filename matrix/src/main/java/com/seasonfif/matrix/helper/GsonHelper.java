package com.seasonfif.matrix.helper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by seasonfif on 2017/5/20.
 */
public class GsonHelper {

    private static Gson sGson = new Gson();

    /**
     * 通过json字符串判断是否为JSONArray
     * @param json
     * @return
     */
    public static boolean isJSONArray(String json){
        try {
            Object obj = new JSONTokener(json).nextValue();
            if (obj instanceof JSONArray){
                return true;
            }
        } catch (JSONException e) {
            return false;
        }
        return false;
    }

    /**
     * json字符串转为T对象
     * @param jsonStr
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T format(String jsonStr, Class<T> cls){
        return sGson.fromJson(jsonStr, cls);
        /*try{
        }catch (JsonSyntaxException e){
            return null;
        }*/
    }

    /**
     * json字符串转为T的List对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> formatList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<T> arrayList = null;
        try {
            ArrayList<JsonObject> jsonObjects = sGson.fromJson(json, type);
            if (jsonObjects != null && jsonObjects.size() > 0) {
                arrayList = new ArrayList<>();
                for (JsonObject jsonObject : jsonObjects) {
                    arrayList.add(sGson.fromJson(jsonObject, clazz));
                }
            }
        } catch (JsonSyntaxException e) {
            return null;
        }
        return arrayList;
    }
}
