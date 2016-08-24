package com.heima.capter.view;

import android.R.integer;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ����ͷ������ͼƬ�Ļ���
 * 
 * @author Administrator
 * 
 */
public class TopNewsViewPager extends ViewPager {

	private int startX;
	private int startY;
	private int endX;
	private int endY;

	public TopNewsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TopNewsViewPager(Context context) {
		super(context);
	}

	/**
	 * �¼��ַ�, ���󸸿ؼ������ڿؼ��Ƿ������¼� 1. �һ�, �����ǵ�һ��ҳ��, ��Ҫ���ؼ����� 2. ��, ���������һ��ҳ��, ��Ҫ���ؼ�����
	 * 3. ���»���, ��Ҫ���ؼ�����
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);
			startX = (int) ev.getRawX();
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			endX = (int) ev.getRawX();
			endY = (int) ev.getRawY();
			if (Math.abs(endX - startX) > Math.abs(endY - startY)) {
				if (endX > startX) {// �һ�
					if (getCurrentItem() == 0) {
						 /*��һ��ҳ��, ��Ҫ���ؼ�(����㸸�ؼ���slideMenu������ǵڶ����Viewpager)���أ�
						ע��:��������λ�ڵ�һ��ʱ�����һ�����slideMenu���д��������������ڵ�һ��ʱ�ڶ�
						���ViewPager������SlideMenu�����������ֵ��˵�һ��(��������������һ��ViewPager)��
						�������ڵ�һ���ViewPager��ֹ�����һ����¼����������ֵ��ڶ���ViewPager,��Viewpager
						�����һ��������˴���*/
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				} else {//��
					if (getCurrentItem() == getAdapter().getCount() - 1) {// ���һ��ҳ��,
						// ��Ҫ����
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
			} else {// ���»�
				getParent().requestDisallowInterceptTouchEvent(false);
			}

			break;
		}

		// getParent().requestDisallowInterceptTouchEvent(true);// ��getParentȥ����
		return super.dispatchTouchEvent(ev);
	}
}
