package com.longbo.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FocusTextView extends TextView{

	
	//��ϵͳ���ã�������+�����Ļ������췽��+�����ļ�������ʽ�ļ����췽����
	public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	//��ϵͳ���ã�������+�����Ļ������췽����
	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	//ʹ����java���봴���ؼ�
	public FocusTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	//��д��ȡ����ķ���,ϵͳ���ã����õ�ʱ��Ĭ�Ͼ��ܻ�ȡ����
	@Override
	public boolean isFocused() {
		return true;
	}
	
}
