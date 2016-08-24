package com.heima.capter.base;

import android.app.Activity;
import android.view.View;

/**
 * 用于显示五个pager中间的主要内容(即去掉上部title之后的pager内容)
 */
public abstract class BaseMenuDetailPager {
	public Activity mActivity;
	/**
	 * 是个View对象(在BaseMenuDetailPager中定义，赋值是mRootView = initViews();)
	 * 即通过子类NewsMenuDetailPager等实现initView()对其进行填充，
	 * 而填充之后将填充好的View对象添加到BasePager类的布局文件的fragment中，作
	 * 为BasePager布局文件中fl_content的内容，显示在主页的中部
	 * 注意BasePager的布局文件还有一个设置title和btn布局，将填充好的fl_content加上title和
	 * btn一起又组成了定义在BasePager中的mRootView，而BasePager中的mRootView又添加到
	 * contentFragment中的适配器，使得上半部分加到最外层(1)ViewPager中
	 */
	public View mRootView;

	public BaseMenuDetailPager(Activity activity) {
		mActivity = activity;
		mRootView = initViews();
	}

	/**
	 * 初始化界面
	 */
	public abstract View initViews();

	/**
	 * 初始化数据
	 */
	public void initData() {

	}
}
