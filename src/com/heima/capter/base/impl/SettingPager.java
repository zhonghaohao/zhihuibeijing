package com.heima.capter.base.impl;

import com.heima.capter.base.BasePager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


/**
 * ����ҳ��
 * 
 * @author Kevin
 * 
 */
public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		/**
		 * ���ڲ��Կ�ViewPager�Ƿ�Ԥ����
		 */
		System.out.println("��ʼ����������....");
		
		tvTitle.setText("����");
		// ���ز���˵�����ť
		btnMenu.setVisibility(View.GONE);
		
		/**
		 *  �����Ƿ���Դ򿪲����
		 */
		setSlidingMenuEnable(false);

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
