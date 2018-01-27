package com.longbo.mobilesafe.view;

import com.longbo.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {

	private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.longbo.mobilesafe";
	private static final String tag = "SettingItemView";
	private CheckBox cb_box;
	private TextView tv_des;
	private TextView tv_title;
	private String mDestitle;
	private String mDesoff;
	private String mDeson;

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
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_des = (TextView) findViewById(R.id.tv_des);
		cb_box = (CheckBox) findViewById(R.id.cb_box);
		
		//��ȡ�Զ����Լ�ԭ�����ԵĲ�����AttributeSet attrs�����ȡ
		initAttrs(attrs);
		tv_title.setText(mDestitle);
		
	}
	
	/**
	 * @param attrs ���췽����ά���õ����Լ��ϣ����ؼ������Զ�������Լ���
	 */
	private void initAttrs(AttributeSet attrs) {
		//��ȡ���Ե��ܸ���
//		Log.i(tag, "attrs.getAttributeCount() = " +attrs.getAttributeCount());
//		//��ȡ���������Լ�����ֵ
//		for(int i=0;i<attrs.getAttributeCount(); i++){
//			
//		}
		
		//ͨ�������ռ�+�������ƻ�ȡ����ֵ
		mDestitle = attrs.getAttributeValue(NAMESPACE, "destitle");
		mDesoff = attrs.getAttributeValue(NAMESPACE, "desoff");
		mDeson = attrs.getAttributeValue(NAMESPACE, "deson");
		
		
	}

	/** �ж��Ƿ����ķ���
	 * @return ����SettingItemView�Ƿ�ѡ��״̬ true������checkBox����true�� false�ر� ��checkBox����false��
	 */
	public boolean isCheck(){
		//��checkBox��ѡ�н����������ǰ��Ŀ�Ƿ���
		return cb_box.isChecked();
	}
	
	/**
	 * @param isCheck �Ƿ���Ϊ�����ı������ɵ��������ȥ�� ����
 	 */
	public void setCheck(boolean isCheck){
		//��ǰ��Ŀ��ѡ��Ĺ����У�cb_boxѡ��״̬Ҳ�ڸ���isCheck�仯
		cb_box.setChecked(isCheck);
		if(isCheck){
			//����
			tv_des.setText(mDeson);
		}else{ 
			//�ر�
			tv_des.setText(mDesoff);
		}
	}
	
	

}
