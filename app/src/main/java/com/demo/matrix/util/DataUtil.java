package com.demo.matrix.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * 数据处理工具类
 */
public class DataUtil {

  private static final String TAG = "DataUtil";
  /**
   * GSON转换类
   */
  public static Gson mGson = new Gson();

  public static String toJson(Object object) {
    return mGson.toJson(object);
  }

  /**
   * 将JSON字符串转化成对象
   */
  public static <T> T getData(String result, Class<T> cls) {
    try {
      return mGson.fromJson(result, cls);
    } catch (JsonSyntaxException e) {
      Log.e(TAG, e.toString());
      return null;
    }
  }

  public static <T> ArrayList<T> getListData(String json, Class<T> clazz) {
    Type type = new TypeToken<ArrayList<JsonObject>>() {
    }.getType();
    ArrayList<T> arrayList = null;
    try {
      ArrayList<JsonObject> jsonObjects = mGson.fromJson(json, type);
      if (jsonObjects != null && jsonObjects.size() > 0) {
        arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
          arrayList.add(mGson.fromJson(jsonObject, clazz));
        }
      }
    } catch (JsonSyntaxException e) {
      Log.e(TAG, e.toString());
      return null;
    }
    return arrayList;
  }

  /**
   * @description 通过assets文件获取json数据，简单没做循环判断。
   *
   * @return Json数据（String）
   */
  public static String getStrFromAssets(InputStream inputStream) {
    StringBuilder stringBuilder = new StringBuilder();
    try {
      BufferedReader bf = new BufferedReader(new InputStreamReader(
              inputStream));
      String line;
      while ((line = bf.readLine()) != null) {
        stringBuilder.append(line);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return stringBuilder.toString();
  }
}
