package com.heima.capter.base.impl;

import com.heima.capter.base.BasePager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


/**
 * �ǻ۷���ҳ��
 * 
 * @author Kevin
 * 
 */
public class SmartServicePager extends BasePager {

	public SmartServicePager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		/**
		 * ���ڲ��Կ�ViewPager�Ƿ�Ԥ����
		 */
		System.out.println("��ʼ���ǻ۷�������....");

		tvTitle.setText("����");
		/**
		 *  ��ʾ������˵���ť
		 */
		btnMenu.setVisibility(View.VISIBLE);
		/**
		 *  �����Ƿ���Դ򿪲����
		 */
		setSlidingMenuEnable(true);

		TextView text = new TextView(mActivity);
		text.setText("�ǻ۷���");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);

		/**
		 * ��FrameLayout�ж�̬��Ӳ���
		 * ���ﲻ��ʵ�֣�ֻ�Ǹ���Text
		 */
		flContent.addView(text);
	}

}

