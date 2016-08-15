package com.heima.capter.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.heima.capter.base.BasePager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * 首页实现
 * 
 * @author Kevin
 * 
 */
public class HomePager extends BasePager {

	public HomePager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		/**
		 * 用于测试看ViewPager是否预加载
		 */
		System.out.println("初始化首页数据....");
		
		tvTitle.setText("智慧北京");// 修改标题
		/**
		 * 隐藏侧面菜单栏按钮
		 */
		btnMenu.setVisibility(View.GONE);
		/**
		 *  设置是否可以打开侧边栏
		 */
		setSlidingMenuEnable(false);

		TextView text = new TextView(mActivity);
		text.setText("首页");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);

		/**
		 * 向FrameLayout中动态添加布局
		 * 这里不是实现，只是给个Text
		 */
		flContent.addView(text);
	}

}

