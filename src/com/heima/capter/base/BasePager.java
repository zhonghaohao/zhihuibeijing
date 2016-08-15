package com.heima.capter.base;


import com.heima.capter.R;
import com.heima.capter.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 主页下5个子页面的基类
 * 
 * @author Administrator
 * 
 */
public class BasePager {
	/**
	 * BasePager的mActivity
	 */
	public Activity mActivity;
	/**
	 * 布局对象
	 */
	public View mRootView;
	/**
	 * 页面标题(title)对象
	 */
	public TextView tvTitle;
	/**
	 * 页面内容的一个View之一
	 * 只不过的是个Fragment布局
	 * 用于填充中部的内容
	 */
	public FrameLayout flContent;
	/**
	 * 位于页面toolBar上用于侧滑的按钮
	 */
	public ImageButton btnMenu;

	public BasePager(Activity activity) {
		mActivity = activity;
		initViews();
	}

	/**
	 * 初始化布局将5个子类要用的ID全部进行初始化
	 */
	private void initViews() {
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);

		tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
		flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
		btnMenu = (ImageButton) mRootView.findViewById(R.id.btn_menu);
	}
	
	/**
	 * 初始化数据
	 */
	public void initData() {

	}
	
	/**
	 * 设置侧边栏开启或关闭
	 * 主要是为了在首页和设置不显示侧边栏开关，而在政务，智慧服务，新闻中心显示侧边栏开关
	 * @param enable
	 */
	public void setSlidingMenuEnable(boolean enable) {
		MainActivity mainUi = (MainActivity) mActivity;

		SlidingMenu slidingMenu = mainUi.getSlidingMenu();

		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
	

}
