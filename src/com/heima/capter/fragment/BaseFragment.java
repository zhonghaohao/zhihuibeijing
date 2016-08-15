package com.heima.capter.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作为基类,由侧边菜单栏和主页内容栏去继承实现
 * @author Administrator
 *
 */
public abstract class BaseFragment extends Fragment {
	/**
	 * 注意定义为公共，否则其他类不能访问，为了后期的使用方便
	 */
	public Activity mActivity;

	/**
	 *  fragment创建
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	/**
	 *  处理fragment的布局
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initViews();
	}
	
	/**
	 *  依附的activity创建完成
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initData();
	}

	/**
	 *  子类必须实现初始化布局的方法
	 * @return
	 */
	public abstract View initViews();

	/**
	 *  初始化数据, 可以不实现---因为有的布局不需要初始化数据
	 */
	public void initData() {

	}
}
