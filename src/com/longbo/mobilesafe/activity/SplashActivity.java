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
		//ȥ������
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		initUI();
		initData();
	}

	/**
	 * 
	 */
	private void initData() {
		tv_version_name.setText("�汾���ƣ�"+getVersionName());
		//1.��⣨���ذ汾�źͷ���˺ŶԱȣ��Ƿ��и��£���ʾ�û�����
		//2.���ذ汾��
		mLocalVersionCode = getVersionCode();
		//3.��ȡ�������汾�ţ��ͻ������󣬷������Ӧ(json, xml)��
		//http://www.oxx.com/update.json?key=value 
		//json���ݰ��������°汾����
		/*
		 * �°汾������
		 * 
		 * ����˱����
		 * 
		 * apk�°汾���ص�ַ
		 * */
		
		checkVersion();
	}

	/**
	 * ���汾��
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
				//���������ȡ���ݣ�������Ϊ����json�����ӵ�ַ,�����
				//������ģ�������ʵ��Ե�tomcat
				try {
					//1.��װurl��ַ
					URL url = new URL("http://10.0.2.2:8080/updateMobileSafe.json");
					//2.��������
					HttpURLConnection connection = (HttpURLConnection)url.openConnection();
					//3.���ó�������������ͷ��
					
					//����ʱ
					connection.setConnectTimeout(2000);
					//��ȡ��ʱ
					connection.setReadTimeout(2000);
					
					//Ĭ��GET����ʽ
//					connection.setRequestMethod("POST");
					
					//4.��ȡ��Ӧ��,����ɹ�
					if(connection.getResponseCode() == 200){
						//5.�����ķ�ʽ
						InputStream is = connection.getInputStream();
						//6.����ת�����ַ���(�������װ)
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

	/**���ذ汾��
	 * 	��0 ������ȡ�ɹ�
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
	 * ��ʼ��UI
	 */
	private void initUI() {
		tv_version_name = (TextView)findViewById(R.id.tv_version_name);
	}

}
