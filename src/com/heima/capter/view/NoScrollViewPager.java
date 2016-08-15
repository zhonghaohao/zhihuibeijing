package com.heima.capter.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ȥ����ͨViewPager�������һ����ı׶�
 * 
 * @author Administrator
 * 
 */
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollViewPager(Context context) {
		super(context);
	}

	/**
	 * ����false��ʾ�����أ�ͨ����onTouchEvent()��ϣ� ֻҪViewpager����Viewû�д����¼����¼��ͻ���ʧ
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

	/**
	 * ͨ����дonTouchEvent()�¼� ��ʾ�¼��Ƿ�����, ����false��ʾ������ ��ViewPager���������¼������������һ���
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}
}
