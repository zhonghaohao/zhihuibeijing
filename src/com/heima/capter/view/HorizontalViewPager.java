package com.heima.capter.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * ���ڽ��ViewPager�¼������²���ViewPager��
 * ��ʱû���ã���Ϊͷ�����ź������ͻ(����������м䲿�ֵ�ViewPager��)
 * �������м��ViewPager�ǲ�Ҫ������߲˵���
 * @author Administrator
 *
 */
public class HorizontalViewPager extends ViewPager {

	public HorizontalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HorizontalViewPager(Context context) {
		super(context);
	}

	/**
	 * �¼��ַ�, ���󸸿ؼ������ڿؼ��Ƿ������¼�
	 * �������м��ViewPagerʱ����Ҫ������߲˵���
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(getCurrentItem() != 0){//���ǵ�һ��ҳ�治����
			getParent().requestDisallowInterceptTouchEvent(true);// ��getParentȥ����
		}else{//����ǵ�һ��ҳ�������
			getParent().requestDisallowInterceptTouchEvent(false);// ��getParentȥ����
		}
		return super.dispatchTouchEvent(ev);
	}
}
