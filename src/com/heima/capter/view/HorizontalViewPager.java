package com.heima.capter.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * 用于解决ViewPager事件（中下部的ViewPager）
 * 暂时没有用，因为头条新闻和这个冲突(这个是用于中间部分的ViewPager的)
 * 处理滑动中间的ViewPager是不要划出侧边菜单栏
 * @author Administrator
 *
 */
public class HorizontalViewPager extends ViewPager {

	public HorizontalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HorizontalViewPager(Context context) {
		super(context);
	}

	/**
	 * 事件分发, 请求父控件及祖宗控件是否拦截事件
	 * 当滑动中间的ViewPager时，不要画出侧边菜单栏
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(getCurrentItem() != 0){//不是第一个页面不拦截
			getParent().requestDisallowInterceptTouchEvent(true);// 用getParent去请求
		}else{//如果是第一个页面就拦截
			getParent().requestDisallowInterceptTouchEvent(false);// 用getParent去请求
		}
		return super.dispatchTouchEvent(ev);
	}
}
