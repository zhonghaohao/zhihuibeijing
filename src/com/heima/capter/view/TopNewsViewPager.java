package com.heima.capter.view;

import android.R.integer;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 处理头条新闻图片的滑动
 * 
 * @author Administrator
 * 
 */
public class TopNewsViewPager extends ViewPager {

	private int startX;
	private int startY;
	private int endX;
	private int endY;

	public TopNewsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TopNewsViewPager(Context context) {
		super(context);
	}

	/**
	 * 事件分发, 请求父控件及祖宗控件是否拦截事件 1. 右划, 而且是第一个页面, 需要父控件拦截 2. 左划, 而且是最后一个页面, 需要父控件拦截
	 * 3. 上下滑动, 需要父控件拦截
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			startX = (int) ev.getRawX();
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			endX = (int) ev.getRawX();
			endY = (int) ev.getRawY();
			if (Math.abs(endX - startX) > Math.abs(endY - startY)) {
				if (endX > startX) {// 右滑
					if (getCurrentItem() == 0) {
						 /*第一个页面, 需要父控件(最外层父控件是slideMenu，其次是第二层的Viewpager)拦截，
						注意:当导航栏位于第一个时，向右滑动，slideMenu进行处理，当导航栏不在第一个时第二
						层的ViewPager限制了SlideMenu滑动，所以轮到了第一层(包括顶部栏的那一层ViewPager)，
						但是由于第一层的ViewPager禁止了左右滑动事件，所以又轮到第二层ViewPager,而Viewpager
						对左右滑动进行了处理*/
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				} else {//左滑
					if (getCurrentItem() == getAdapter().getCount() - 1) {// 最后一个页面,
						// 需要拦截
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
			} else {// 上下滑
				getParent().requestDisallowInterceptTouchEvent(false);
			}

			break;
		}

		// getParent().requestDisallowInterceptTouchEvent(true);// 用getParent去请求
		return super.dispatchTouchEvent(ev);
	}
}
