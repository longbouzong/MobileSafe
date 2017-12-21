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
	 * �����°汾��״̬
	 */
	protected static final int UPDATE_VERSION = 100;


	/**
	 * ����Ӧ�ó����������״̬
	 */
	protected static final int ENTER_HOME = 101;


	/**
	 * URL��ַ����״̬
	 */
	protected static final int URL_ERROE = 102;


	/**
	 * IO����״̬
	 */
	protected static final int IO_ERROE = 103;


	/**
	 * json��������״̬
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
				//����Ӧ�ó���������
				enterHome();
				break;
			case URL_ERROE:
//				Toast.makeText(context, text, duration).show();
//				ToastUtil.show(getApplicationContext(), "url�쳣");
				ToastUtil.show(SplashActivity.this, "url�쳣");
				enterHome();
				break;	
			case IO_ERROE:
//				Toast.makeText(context, text, duration).show();
				ToastUtil.show(getApplicationContext(), "��ȡIO�쳣");
				enterHome();
				break;
			case JSON_ERROR:
				ToastUtil.show(getApplicationContext(), "json�����쳣");
				enterHome();
				break;
			}
		}
	};

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
	 * �����Ի�����ʾ�û�����
	 */
	protected void showUpdateDialog() {
		//�Ի�����������activity���ڵ�
		Builder builder = new AlertDialog.Builder(this);
		//�������Ͻ�ͼ��
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle("�汾����");
		//������������
		builder.setMessage(mVersionDes);
		//������ť����������
		builder.setPositiveButton("��������", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//����apk��downloadUrl
				downloadApk();
			}
		});
		
		builder.setNegativeButton("�Ժ���˵", new DialogInterface.OnClickListener(){

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
		//apk���ӵ�ַ������apk����·��
		
		//1.�ж�sd���Ƿ���ã��Ƿ������
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			//2.��ȡSD·��
			String path = Environment.getExternalStorageDirectory().getAbsolutePath()
					+File.separator+"mobilesafe.apk ";
			//3.�������󣬻�ȡapk�����ҷ��õ�ָ��·��
			HttpUtils httpUtils = new HttpUtils();
			
			//4.�������󣬴��ݲ��������ص�ַ������Ӧ�÷���λ�ã�
			httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
				
				@Override
				public void onSuccess(ResponseInfo<File> responseInfo) {
					// ���سɹ�
					Log.i(tag, "���سɹ�");
					File file = responseInfo.result;
					//��ʾ�û���װ
					installApk(file);
				}
				
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					Log.i(tag, "����ʧ��");
					// ����ʧ��
					
				}
				//�ոտ�ʼ���صķ���
				@Override
				public void onStart() {
					Log.i(tag, "�ոտ�ʼ����...");
					super.onStart();
				}
				
				//���ع��̵ķ���������APK�ܴ�С����ǰ������λ�ã��Ƿ��������أ�
				/* (non-Javadoc) 
				 * @see com.lidroid.xutils.http.callback.RequestCallBack#onLoading(long, long, boolean)
				 */
				@Override
				public void onLoading(long total, long current, boolean isUploading) {
					Log.i(tag, "������...");
					Log.i(tag, "total..."+total);
					Log.i(tag, "current..."+current);
					Log.i(tag, "isUploading..."+isUploading);
					super.onLoading(total, current, isUploading);
				}
			});
		}
		
	}

	/**��װ��Ӧapk
	 * @param file
	 */
	protected void installApk(File file) {
		//ϵͳӦ�ý��棬Դ�룬��װapk���
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
//		intent.setData(Uri.fromFile(file));
//		//���ð�װ����
//		intent.setType("application/vnd.android.package-archive");
		
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//		startActivity(intent);
		startActivityForResult(intent, 0);
		
	}
	
	/* (non-Javadoc) ����һ��activity�󣬷��ؽ�����õķ���
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		enterHome();
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	/**
	 * ����Ӧ�ó����������
	 */
	protected void enterHome() {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		
		//�ڿ���һ���µĽ���󣬽���������ر�
		finish();
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
				Message msg = Message.obtain();
				long startTime = System.currentTimeMillis();
				
				
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
						
						//����json
						JSONObject jsonObject = new JSONObject(json);
						mVersionName=jsonObject.getString("versionName");
						mVersionDes=jsonObject.getString("versionDes");
						String versionCode=jsonObject.getString("versionCode");
						mDownloadUrl=jsonObject.getString("downloadUrl");
						
						//8.�ȶ԰汾��
						if(mLocalVersionCode < Integer.parseInt(versionCode)){
							//��ʾ�û����£������Ի���UI������Ϣ����
							msg.what = UPDATE_VERSION;
						}
						else{
							//����Ӧ�ó���
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
					//ָ��˯��ʱ�䣬���������ʱ������4����������
					//��������ʱ��С��4�룬ǿ������˯��������
					
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
