package com.heima.capter.base.impl;

import com.heima.capter.base.BasePager;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


/**
 * 政务
 * 
 * @author Kevin
 * 
 */
public class GovAffairsPager extends BasePager {

	public GovAffairsPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		/**
		 * 用于测试看ViewPager是否预加载
		 */
		System.out.println("初始化政务数据....");
		
		tvTitle.setText("人口管理");
		/**
		 *  显示侧边栏菜单按钮
		 */
		btnMenu.setVisibility(View.VISIBLE);
		/**
		 *  设置是否可以打开侧边栏
		 */
		setSlidingMenuEnable(true);

		TextView text = new TextView(mActivity);
		text.setText("政务");
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

