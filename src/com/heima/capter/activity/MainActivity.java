package com.heima.capter.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.heima.capter.R;
import com.heima.capter.fragment.ContentFragment;
import com.heima.capter.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * ��ҳ��
 * 
 * @author Administrator
 */
public class MainActivity extends SlidingFragmentActivity {
	/**
	 * ����Fragment�ı��
	 */
	private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
	/**
	 * ��ҳ��Fragment�ı��
	 */
	private static final String FRAGMENT_CONTENT = "fragment_content";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//���Ҫ�������ò��ּ�setContentView()֮ǰ����
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.left_menu);//���ò����
		
		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//���û�����ģʽ----�˴�Ϊȫ�������Ի���
		slidingMenu.setBehindOffset(200);//���û���֮��Ԥ������Ļ��С(���)
		
		initFragment();
		
	}
	/**
	 * ��ʼ���������
	 * 1.��lefeMenuFragment������߲˵�
	 * 		������lefeMenuFragment���滹�Ǽ�����һ������(R.layout.fragment_left_menu,����һ��View,View��һ��listView)
	 * 2.��ContentFragment������ҳ
	 * 		������ContentFragment���滹�Ǽ�����һ������(R.layout.fragment_content,����һ��ViewPager�ͶԵײ������Radio����)
	 */
	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),
				FRAGMENT_LEFT_MENU);
		transaction.replace(R.id.fl_content, new ContentFragment(),
				FRAGMENT_CONTENT);
		transaction.commit();	
	}
	
	/**
	 *  ��ȡ�����fragment
	 * @return	���������Fragment����
	 */
		public LeftMenuFragment getLeftMenuFragment() {
			FragmentManager fm = getSupportFragmentManager();
			LeftMenuFragment fragment = (LeftMenuFragment) fm
					.findFragmentByTag(FRAGMENT_LEFT_MENU);

			return fragment;
		}

		/**
		 *  ��ȡ��ҳ��fragment
		 * @return  ������ݵ�fragment����
		 */
		public ContentFragment getContentFragment() {
			FragmentManager fm = getSupportFragmentManager();
			ContentFragment fragment = (ContentFragment) fm
					.findFragmentByTag(FRAGMENT_CONTENT);

			return fragment;
		}
	
	
	
	
	
	
	
}
