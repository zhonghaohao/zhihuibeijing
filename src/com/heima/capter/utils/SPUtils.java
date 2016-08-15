package com.heima.capter.utils;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * SharedPreferences�Ĳ���������
 * @author Administrator
 *
 */
public class SPUtils {
	
   private static String PREF_NAME = "configs";
   public static Boolean getBoolean(Context context, String key, Boolean defaultValue){
	   SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	   Boolean value =  sp.getBoolean(key, defaultValue);
	   return value;
   }
	
   public static void setBoolean(Context context, String key, Boolean value){
	   SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	   sp.edit().putBoolean(key, value).commit();
   }
}
