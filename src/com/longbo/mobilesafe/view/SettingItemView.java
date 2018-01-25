package com.longbo.mobilesafe.view;

import com.longbo.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {

	public SettingItemView(Context context) {
		this(context, null);
		
	}

	public SettingItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		
	}

	public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//xm--->view 将设置界面的一个条目转化成view对象,直接添加到了当前新SettingItemView对应的view中
//		View.inflate(context, R.layout.setting_item_view, this);
		View view = View.inflate(context, R.layout.setting_item_view, null);
		this.addView(view);
		
		//自定义控件中的标题描述
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		TextView tv_des = (TextView) findViewById(R.id.tv_des);
		TextView cb_box = (TextView) findViewById(R.id.cb_box);
	}

}
