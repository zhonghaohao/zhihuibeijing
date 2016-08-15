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
	 * 返回false表示不拦截，通过与onTouchEvent()配合， 只要Viewpager的子View没有处理事件，事件就会消失
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
