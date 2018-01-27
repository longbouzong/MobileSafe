package com.longbo.mobilesafe.activity;

import com.longbo.mobilesafe.R;
import com.longbo.mobilesafe.utils.ConstantValue;
import com.longbo.mobilesafe.utils.SpUtils;
import com.longbo.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		initUpdate();
	}

	private void initUpdate() {
		final SettingItemView siv_update = (SettingItemView)findViewById(R.id.siv_update);
		//��ȡ���еĿ���״̬��������ʾ
		boolean open_update = SpUtils.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
		
		//�Ƿ�ѡ�У�������һ�δ洢�Ľ��ȥ������
		siv_update.setCheck(open_update);
		
		siv_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 
				//��ȡ֮ǰ��ѡ��״̬
				boolean isCheck = siv_update.isCheck();
				  
				//��ԭ��״̬ȡ������ͬ��������������
				siv_update.setCheck(!isCheck);
				
				//��ȡ�����״̬�洢����Ӧ��sp��
				SpUtils.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE, !isCheck);
			}
		});
	}
}
