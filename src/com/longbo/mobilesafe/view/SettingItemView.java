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
		//xm--->view �����ý����һ����Ŀת����view����,ֱ����ӵ��˵�ǰ��SettingItemView��Ӧ��view��
//		View.inflate(context, R.layout.setting_item_view, this);
		View view = View.inflate(context, R.layout.setting_item_view, null);
		this.addView(view);
		
		//�Զ���ؼ��еı�������
		TextView tv_title = (TextView) findViewById(R.id.tv_title);
		TextView tv_des = (TextView) findViewById(R.id.tv_des);
		TextView cb_box = (TextView) findViewById(R.id.cb_box);
	}

}
