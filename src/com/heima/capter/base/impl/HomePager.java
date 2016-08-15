package com.heima.capter.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.heima.capter.base.BasePager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

/**
 * ��ҳʵ��
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
		 * ���ڲ��Կ�ViewPager�Ƿ�Ԥ����
		 */
		System.out.println("��ʼ����ҳ����....");
		
		tvTitle.setText("�ǻ۱���");// �޸ı���
		/**
		 * ���ز���˵�����ť
		 */
		btnMenu.setVisibility(View.GONE);
		/**
		 *  �����Ƿ���Դ򿪲����
		 */
		setSlidingMenuEnable(false);

		TextView text = new TextView(mActivity);
		text.setText("��ҳ");
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

