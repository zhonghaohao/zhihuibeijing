package com.heima.capter.base;


import com.heima.capter.R;
import com.heima.capter.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
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
	 * 这是BasePager的布局对象是一个View而不是id
	 * 在ContentFragment中有用到,用于子类NewsCenterPager等把
	 * 主页上半部分补充完整,注意BaseMenuDetailPager中也有一
	 * 个mRootView,它是用NewsMenuDetailPager等子类填充
	 * 的，用于填充本类中的flContent，使整个上半部分完整
	 */
	public View mRootView;
	/**
	 * 页面标题(title)对象
	 */
	public TextView tvTitle;
	/**
	 * 这个在BasePager里面，是一个不包括顶部，只有中部的fragmeLayout
	 * 注意basePager有顶部，它只是一部分
	 * 页面内容的一个View，是base.XMl中的，并不是Main.XML
	 * 只不过的是个Fragment布局，显示的是主页中部的内容，使用NewsMenuDetailPager的
	 * 内容进行填充
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
		btnMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleSlidingMenu();
			}
		});
	}
	
	/**
	 * 切换SlidingMenu的状态
	 * 只要点击了侧边栏任意一项就收起侧边栏
	 * 切换状态, 显示时隐藏, 隐藏时显示
	 */
	protected void toggleSlidingMenu() {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();
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
