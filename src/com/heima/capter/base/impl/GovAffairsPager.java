package com.heima.capter.base.impl;

import com.heima.capter.base.BasePager;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


/**
 * ����
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
		 * ���ڲ��Կ�ViewPager�Ƿ�Ԥ����
		 */
		System.out.println("��ʼ����������....");
		
		tvTitle.setText("�˿ڹ���");
		/**
		 *  ��ʾ������˵���ť
		 */
		btnMenu.setVisibility(View.VISIBLE);
		/**
		 *  �����Ƿ���Դ򿪲����
		 */
		setSlidingMenuEnable(true);

		TextView text = new TextView(mActivity);
		text.setText("����");
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

