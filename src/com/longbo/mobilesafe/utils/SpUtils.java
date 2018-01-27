package com.longbo.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

		private static SharedPreferences sp;

		//读
		/**
		 * @param ctx
		 * @param key
		 * @param value
		 */
		public static void putBoolean(Context ctx, String key, boolean value){
			//存储节点文件的名称，读写方式
			if(sp == null)
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
			sp.edit().putBoolean(key, value).commit();
		}

		//写
		public static boolean getBoolean(Context ctx, String key, boolean defValue){
			//存储节点文件的名称，读写方式
			if(sp == null)
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
			return sp.getBoolean(key, defValue);
		}
	

}
