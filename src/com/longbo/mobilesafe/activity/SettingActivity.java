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
		//获取已有的开关状态，用作显示
		boolean open_update = SpUtils.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
		
		//是否选中，根据上一次存储的结果去做决定
		siv_update.setCheck(open_update);
		
		siv_update.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 
				//获取之前的选中状态
				boolean isCheck = siv_update.isCheck();
				  
				//将原有状态取反，等同于上述两部操作
				siv_update.setCheck(!isCheck);
				
				//将取反后的状态存储到相应的sp中
				SpUtils.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE, !isCheck);
			}
		});
	}
}
