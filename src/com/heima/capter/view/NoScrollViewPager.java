package com.heima.capter.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 去除普通ViewPager可以左右滑动的弊端
 * 
 * @author Administrator
 * 
 */
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollViewPager(Context context) {
		super(context);
	}

	/**
	 * 为了处理在主页面1(除底部)和主页面2中间部分(除底部和顶部)的两个ViewPager的冲突
	 * 要求：限制主页面1的滑动事件，又不限制主页面2的滑动
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

	/**
	 * 通过重写onTouchEvent()事件 表示事件是否拦截, 返回false表示不拦截 即ViewPager不处理滑动事件，即不能左右滑动
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}
}
