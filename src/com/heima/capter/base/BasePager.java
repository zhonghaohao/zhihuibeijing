package com.heima.capter.base;


import com.heima.capter.R;
import com.heima.capter.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * ��ҳ��5����ҳ��Ļ���
 * 
 * @author Administrator
 * 
 */
public class BasePager {
	/**
	 * BasePager��mActivity
	 */
	public Activity mActivity;
	/**
	 * ����BasePager�Ĳ��ֶ�����һ��View������id
	 * ��ContentFragment�����õ�,��������NewsCenterPager�Ȱ�
	 * ��ҳ�ϰ벿�ֲ�������,ע��BaseMenuDetailPager��Ҳ��һ
	 * ��mRootView,������NewsMenuDetailPager���������
	 * �ģ�������䱾���е�flContent��ʹ�����ϰ벿������
	 */
	public View mRootView;
	/**
	 * ҳ�����(title)����
	 */
	public TextView tvTitle;
	/**
	 * �����BasePager���棬��һ��������������ֻ���в���fragmeLayout
	 * ע��basePager�ж�������ֻ��һ����
	 * ҳ�����ݵ�һ��View����base.XMl�еģ�������Main.XML
	 * ֻ�������Ǹ�Fragment���֣���ʾ������ҳ�в������ݣ�ʹ��NewsMenuDetailPager��
	 * ���ݽ������
	 */
	public FrameLayout flContent;
	/**
	 * λ��ҳ��toolBar�����ڲ໬�İ�ť
	 */
	public ImageButton btnMenu;

	public BasePager(Activity activity) {
		mActivity = activity;
		initViews();
	}

	/**
	 * ��ʼ�����ֽ�5������Ҫ�õ�IDȫ�����г�ʼ��
	 */
	private void initViews() {
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);

		tvTitle = (TextView) mRootView.findViewById(R.id.tv_title);
		flContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
		btnMenu = (ImageButton) mRootView.findViewById(R.id.btn_menu);
		btnMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleSlidingMenu();
			}
		});
	}
	
	/**
	 * �л�SlidingMenu��״̬
	 * ֻҪ����˲��������һ�����������
	 * �л�״̬, ��ʾʱ����, ����ʱ��ʾ
	 */
	protected void toggleSlidingMenu() {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();
	}
	
	/**
	 * ��ʼ������
	 */
	public void initData() {

	}
	
	/**
	 * ���ò����������ر�
	 * ��Ҫ��Ϊ������ҳ�����ò���ʾ��������أ����������ǻ۷�������������ʾ���������
	 * @param enable
	 */
	public void setSlidingMenuEnable(boolean enable) {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
	

}
