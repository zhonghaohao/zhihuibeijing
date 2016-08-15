package com.heima.capter.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ��Ϊ����,�ɲ�߲˵�������ҳ������ȥ�̳�ʵ��
 * @author Administrator
 *
 */
public abstract class BaseFragment extends Fragment {
	/**
	 * ע�ⶨ��Ϊ���������������಻�ܷ��ʣ�Ϊ�˺��ڵ�ʹ�÷���
	 */
	public Activity mActivity;

	/**
	 *  fragment����
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	/**
	 *  ����fragment�Ĳ���
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return initViews();
	}
	
	/**
	 *  ������activity�������
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initData();
	}

	/**
	 *  �������ʵ�ֳ�ʼ�����ֵķ���
	 * @return
	 */
	public abstract View initViews();

	/**
	 *  ��ʼ������, ���Բ�ʵ��---��Ϊ�еĲ��ֲ���Ҫ��ʼ������
	 */
	public void initData() {

	}
}
