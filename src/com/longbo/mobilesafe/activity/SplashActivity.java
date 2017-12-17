package com.longbo.mobilesafe.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.longbo.mobilesafe.R;
import com.longbo.mobilesafe.utils.StreamUtil;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SplashActivity extends Activity {

	protected static final String tag = "SplashActivity";
	private TextView tv_version_name;
	private int mLocalVersionCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除标题
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		initUI();
		initData();
	}

	/**
	 * 
	 */
	private void initData() {
		tv_version_name.setText("版本名称："+getVersionName());
		//1.检测（本地版本号和服务端号对比）是否有更新，提示用户下载
		//2.本地版本号
		mLocalVersionCode = getVersionCode();
		//3.获取服务器版本号（客户端请求，服务端响应(json, xml)）
		//http://www.oxx.com/update.json?key=value 
		//json内容包括：更新版本名称
		/*
		 * 新版本的描述
		 * 
		 * 服务端本版号
		 * 
		 * apk新版本下载地址
		 * */
		
		checkVersion();
	}

	/**
	 * 检测版本号
	 */
	private void checkVersion() {
//		new Thread(new Runnable(){
//
//			@Override
//			public void run() {
//				
//			}});
		
		new Thread(){
			public void run(){
				//发送请求获取数据，参数则为请求json的链接地址,不灵活
				//仅限于模拟器访问电脑的tomcat
				try {
					//1.封装url地址
					URL url = new URL("http://10.0.2.2:8080/updateMobileSafe.json");
					//2.开启链接
					HttpURLConnection connection = (HttpURLConnection)url.openConnection();
					//3.设置常见参数（请求头）
					
					//请求超时
					connection.setConnectTimeout(2000);
					//读取超时
					connection.setReadTimeout(2000);
					
					//默认GET请求方式
//					connection.setRequestMethod("POST");
					
					//4.获取响应码,请求成功
					if(connection.getResponseCode() == 200){
						//5.以流的方式
						InputStream is = connection.getInputStream();
						//6.将流转换成字符串(工具类封装)
						String json = StreamUtil.streamToString(is);
						Log.i(tag, json);
					}
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}.start();
		
	}

	/**返回版本号
	 * 	非0 则代表获取成功
	 * @return
	 */
	private int getVersionCode() {
		
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	private String getVersionName() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初始化UI
	 */
	private void initUI() {
		tv_version_name = (TextView)findViewById(R.id.tv_version_name);
	}

}
