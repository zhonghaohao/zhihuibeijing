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
 * 主页面
 * 
 * @author Administrator
 */
public class MainActivity extends SlidingFragmentActivity {
	/**
	 * 侧面Fragment的标记
	 */
	private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
	/**
	 * 主页面Fragment的标记
	 */
	private static final String FRAGMENT_CONTENT = "fragment_content";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//这个要求在设置布局即setContentView()之前调用
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.left_menu);//设置侧边栏
		
		SlidingMenu slidingMenu = getSlidingMenu();
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置滑动的模式----此处为全屏都可以滑动
		slidingMenu.setBehindOffset(200);//设置滑动之后，预留的屏幕大小(宽度)
		
		initFragment();
		
	}
	/**
	 * 初始化布局填充
	 * 1.将lefeMenuFragment填充给侧边菜单
	 * 		其中在lefeMenuFragment里面还是加载了一个布局(R.layout.fragment_left_menu,包括一个View,View是一个listView)
	 * 2.将ContentFragment填充给主页
	 * 		其中在ContentFragment里面还是加载了一个布局(R.layout.fragment_content,包括一个ViewPager和对底部的五个Radio定义)
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
	 *  获取侧边栏fragment
	 * @return	填充侧边栏的Fragment布局
	 */
		public LeftMenuFragment getLeftMenuFragment() {
			FragmentManager fm = getSupportFragmentManager();
			LeftMenuFragment fragment = (LeftMenuFragment) fm
					.findFragmentByTag(FRAGMENT_LEFT_MENU);

			return fragment;
		}

		/**
		 *  获取主页面fragment
		 * @return  填充内容的fragment布局
		 */
		public ContentFragment getContentFragment() {
			FragmentManager fm = getSupportFragmentManager();
			ContentFragment fragment = (ContentFragment) fm
					.findFragmentByTag(FRAGMENT_CONTENT);

			return fragment;
		}
	
	
	
	
	
	
	
}
