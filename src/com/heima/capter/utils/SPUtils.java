package com.heima.capter.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences的操作工具类
 * 
 * @author Administrator
 * 
 */
public class SPUtils {

	private static String PREF_NAME = "configs";

	public static Boolean getBoolean(Context context, String key,
			Boolean defaultValue) {
		SharedPreferences sp = context.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		Boolean value = sp.getBoolean(key, defaultValue);
		return value;
	}

	public static void setBoolean(Context context, String key, Boolean value) {
		SharedPreferences sp = context.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit();
	}

	public static String getString(Context ctx, String key, String defaultValue) {
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		return sp.getString(key, defaultValue);
	}

	public static void setString(Context ctx, String key, String value) {
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		sp.edit().putString(key, value).commit();
	}
	public static int getInt(Context ctx, String key, int defaultValue) {
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		return sp.getInt(key, defaultValue);
	}

	public static void setInt(Context ctx, String key, int value) {
		SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME,
				Context.MODE_PRIVATE);
		sp.edit().putInt(key, value).commit();
	}
}
