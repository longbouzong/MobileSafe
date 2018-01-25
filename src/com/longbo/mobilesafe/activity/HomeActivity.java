package com.longbo.mobilesafe.activity;

import java.lang.annotation.Annotation;

import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.longbo.mobilesafe.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {
	private GridView gv_home;
	private String[] mTitleStr;
	private int[] mDrawableIds; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
		initUI();
		
		//��ʼ�����ݵķ���
		initData();
	}

	private void initData() {
		//׼�����ݣ����֣�9�飩��ͼƬ��9�ţ���
		 mTitleStr = new String[]{
				"�ֻ�����", "ͨ����ʿ", "�������",
				"���̹���", "����ͳ��", "�ֻ�ɱ��",
				"��������", "�߼�����", "��������"};
		 
		mDrawableIds = new int[]{
				R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher ,
				R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher ,
				R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher
		};
		
		//�Ź�����������������(��ͬListView����������)
		gv_home.setAdapter(new MyAdapter());
		//ע��Ź��񵥸���Ŀ�ĵ���¼�
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			    //�����б���Ŀ������
				switch(position){
			    	case 8:
			    		Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
			    		startActivity(intent);
			    		break;
			    }
				
			}
		});
		
		
	}

	private void initUI() {
		gv_home = (GridView)findViewById(R.id.gv_home);
		
	}
	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// ��Ŀ������
			return mTitleStr.length;
		}

		@Override
		public Object getItem(int position) {
			return mTitleStr[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		//Holder	
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(), R.layout.gridview_item, null);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			ImageView iv_icon =  (ImageView) view.findViewById(R.id.iv_icon);
			tv_title.setText(mTitleStr[position]);
			iv_icon.setBackgroundResource(mDrawableIds[position]);
			return view;
		}
		
	}
}
