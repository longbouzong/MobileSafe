package com.longbo.mobilesafe.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.longbo.mobilesafe.utils.StreamUtil;
import com.longbo.mobilesafe.utils.ToastUtil;

import com.longbo.mobilesafe.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class SplashActivity extends Activity {

	protected static final String tag = "SplashActivity";
	
	
	/**
	 * 更新新版本的状态
	 */
	protected static final int UPDATE_VERSION = 100;


	/**
	 * 进入应用程序主界面的状态
	 */
	protected static final int ENTER_HOME = 101;


	/**
	 * URL地址出错状态
	 */
	protected static final int URL_ERROE = 102;


	/**
	 * IO出错状态
	 */
	protected static final int IO_ERROE = 103;


	/**
	 * json解析出错状态
	 */
	protected static final int JSON_ERROR = 104;


	private TextView tv_version_name;
	private int mLocalVersionCode;
	protected String mVersionDes;
	protected String mDownloadUrl;
	protected String mVersionName;
	
	
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case UPDATE_VERSION:
				showUpdateDialog();
				break;
			case ENTER_HOME:
				//进入应用程序主界面
				enterHome();
				break;
			case URL_ERROE:
//				Toast.makeText(context, text, duration).show();
//				ToastUtil.show(getApplicationContext(), "url异常");
				ToastUtil.show(SplashActivity.this, "url异常");
				enterHome();
				break;	
			case IO_ERROE:
//				Toast.makeText(context, text, duration).show();
				ToastUtil.show(getApplicationContext(), "读取IO异常");
				enterHome();
				break;
			case JSON_ERROR:
				ToastUtil.show(getApplicationContext(), "json解析异常");
				enterHome();
				break;
			}
		}
	};

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
	 * 弹出对话框，提示用户更新
	 */
	protected void showUpdateDialog() {
		//对话框是依赖于activity存在的
		Builder builder = new AlertDialog.Builder(this);
		//设置左上角图标
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("版本更新");
		//设置描述内容
		builder.setMessage(mVersionDes);
		//积极按钮，立即更新
		builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//下载apk，downloadUrl
				downloadApk();
			}
		});
		
		builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				enterHome();
				
			}
		});
		
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				enterHome();
				dialog.dismiss();
				
			}
		});
	
		builder.show();
	}

	protected void downloadApk() {
		//apk链接地址，放置apk所在路径
		
		//1.判断sd卡是否可用，是否挂在上
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			//2.获取SD路径
			String path = Environment.getExternalStorageDirectory().getAbsolutePath()
					+File.separator+"mobilesafe.apk ";
			//3.发送请求，获取apk，并且放置到指定路径
			HttpUtils httpUtils = new HttpUtils();
			
			//4.发送请求，传递参数（下载地址，下载应用放置位置）
			httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
				
				@Override
				public void onSuccess(ResponseInfo<File> responseInfo) {
					// 下载成功
					Log.i(tag, "下载成功");
					File file = responseInfo.result;
					//提示用户安装
					installApk(file);
				}
				
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Log.i(tag, "下载失败");
					// 下载失败
					
				}
				//刚刚开始下载的方法
				@Override
				public void onStart() {
					Log.i(tag, "刚刚开始下载...");
					super.onStart();
				}
				
				//下载过程的方法（下载APK总大小，当前的下载位置，是否正在下载）
				/* (non-Javadoc) 
				 * @see com.lidroid.xutils.http.callback.RequestCallBack#onLoading(long, long, boolean)
				 */
				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					Log.i(tag, "下载中...");
					Log.i(tag, "total..."+total);
					Log.i(tag, "current..."+current);
					Log.i(tag, "isUploading..."+isUploading);
					super.onLoading(total, current, isUploading);
				}
			});
		}
		
	}

	/**安装对应apk
	 * @param file
	 */
	protected void installApk(File file) {
		//系统应用界面，源码，安装apk入口
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
//		intent.setData(Uri.fromFile(file));
//		//设置安装类型
//		intent.setType("application/vnd.android.package-archive");
		
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//		startActivity(intent);
		startActivityForResult(intent, 0);
		
	}
	
	/* (non-Javadoc) 开启一个activity后，返回结果调用的方法
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		enterHome();
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	/**
	 * 进入应用程序的主界面
	 */
	protected void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		
		//在开启一个新的界面后，将导航界面关闭
		finish();
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
				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();
				
				
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
						
						//解析json
						JSONObject jsonObject = new JSONObject(json);
						mVersionName=jsonObject.getString("versionName");
						mVersionDes=jsonObject.getString("versionDes");
						String versionCode=jsonObject.getString("versionCode");
						mDownloadUrl=jsonObject.getString("downloadUrl");
						
						//8.比对版本号
						if(mLocalVersionCode < Integer.parseInt(versionCode)){
							//提示用户更新，弹出对话框（UI），消息机制
							msg.what = UPDATE_VERSION;
						}
						else{
							//进入应用程序
							msg.what = ENTER_HOME;
						}
					}
					
				} catch (MalformedURLException e) {
					e.printStackTrace();
					msg.what = URL_ERROE;
				} catch (IOException e) {
					e.printStackTrace();
					msg.what = IO_ERROE;
				} catch (JSONException e) {
					e.printStackTrace();
					msg.what = JSON_ERROR;
				}finally {
					//指定睡眠时间，请求网络的时长超过4秒则不做处理
					//请求网络时长小于4秒，强制让其睡眠满四秒
					
					long endTime = System.currentTimeMillis();
					if(endTime - startTime < 4000){
						try {
							Thread.sleep(4000-(endTime - startTime));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					mHandler.sendMessage(msg);
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
