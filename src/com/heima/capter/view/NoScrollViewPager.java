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
	 * Ϊ�˴�������ҳ��1(���ײ�)����ҳ��2�м䲿��(���ײ��Ͷ���)������ViewPager�ĳ�ͻ
	 * Ҫ��������ҳ��1�Ļ����¼����ֲ�������ҳ��2�Ļ���
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
