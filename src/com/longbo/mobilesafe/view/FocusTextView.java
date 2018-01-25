package com.longbo.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class FocusTextView extends TextView{

	
	//由系统调用（带属性+上下文环境构造方法+布局文件定义样式文件构造方法）
	public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	//由系统调用（带属性+上下文环境构造方法）
	public FocusTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	//使用在java代码创建控件
	public FocusTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	//重写获取焦点的方法,系统调用，调用的时候默认就能获取焦点
	@Override
	public boolean isFocused() {
		return true;
	}
	
}
