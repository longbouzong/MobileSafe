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
		
		//初始化数据的方法
		initData();
	}

	private void initData() {
		//准备数据（文字（9组），图片（9张））
		 mTitleStr = new String[]{
				"手机防盗", "通信卫士", "软件管理",
				"进程管理", "流量统计", "手机杀毒",
				"缓存清理", "高级工具", "设置中心"};
		 
		mDrawableIds = new int[]{
				R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher ,
				R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher ,
				R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher
		};
		
		//九宫格设置数据适配器(等同ListView数据适配器)
		gv_home.setAdapter(new MyAdapter());
		//注册九宫格单个条目的点击事件
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			    //点中列表条目的索引
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
			// 条目的总数
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
