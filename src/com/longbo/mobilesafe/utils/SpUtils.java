package com.longbo.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SpUtils {

		private static SharedPreferences sp;

		//��
		/**
		 * @param ctx
		 * @param key
		 * @param value
		 */
		public static void putBoolean(Context ctx, String key, boolean value){
			//�洢�ڵ��ļ������ƣ���д��ʽ
			if(sp == null)
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
			sp.edit().putBoolean(key, value).commit();
		}

		//д
		public static boolean getBoolean(Context ctx, String key, boolean defValue){
			//�洢�ڵ��ļ������ƣ���д��ʽ
			if(sp == null)
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
			return sp.getBoolean(key, defValue);
		}
	

}
