package com.heima.capter.base;

import android.app.Activity;
import android.view.View;

/**
 * ������ʾ���pager�м����Ҫ����(��ȥ���ϲ�title֮���pager����)
 */
public abstract class BaseMenuDetailPager {
	public Activity mActivity;
	/**
	 * �Ǹ�View����(��BaseMenuDetailPager�ж��壬��ֵ��mRootView = initViews();)
	 * ��ͨ������NewsMenuDetailPager��ʵ��initView()���������䣬
	 * �����֮�����õ�View������ӵ�BasePager��Ĳ����ļ���fragment�У���
	 * ΪBasePager�����ļ���fl_content�����ݣ���ʾ����ҳ���в�
	 * ע��BasePager�Ĳ����ļ�����һ������title��btn���֣������õ�fl_content����title��
	 * btnһ��������˶�����BasePager�е�mRootView����BasePager�е�mRootView����ӵ�
	 * contentFragment�е���������ʹ���ϰ벿�ּӵ������(1)ViewPager��
	 */
	public View mRootView;

	public BaseMenuDetailPager(Activity activity) {
		mActivity = activity;
		mRootView = initViews();
	}

	/**
	 * ��ʼ������
	 */
	public abstract View initViews();

	/**
	 * ��ʼ������
	 */
	public void initData() {

	}
}
