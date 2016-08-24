package com.heima.capter.base.menudetail;

import java.util.ArrayList;
import java.util.List;

import com.heima.capter.R;
import com.heima.capter.activity.MainActivity;
import com.heima.capter.base.BaseMenuDetailPager;
import com.heima.capter.base.TabDetailPager;
import com.heima.capter.domain.NewsData.NewsTabData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

/**
 * 菜单详情页-新闻
 * 
 * @author Kevin
 * 
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements
		OnPageChangeListener {

	/**
	 * 11个导航栏的数据(mNewsTabDatas是一个类里面的children对象)
	 */
	public List<NewsTabData> mNewsTabDatas;
	/**
	 * viewpager
	 */
	private ViewPager mViewPager;
	/**
	 * 用于存储导航栏数据，适用于设置ViewPager的list数据
	 */
	private List<TabDetailPager> mPagerList;
	private TabPageIndicator mIndicator;

	/**
	 * 这个构造方法是为了把导航栏的数据传过来
	 * 
	 * @param activity
	 * @param children
	 *            存有导航栏数据的数组名
	 */
	public NewsMenuDetailPager(Activity activity, List<NewsTabData> children) {
		super(activity);
		mNewsTabDatas = children;
	}

	@Override
	public View initViews() {
		// 我写的后期要删除
		// TextView text = new TextView(mActivity);
		// text.setText("菜单详情页-专题");
		// text.setTextColor(Color.RED);
		// text.setTextSize(25);
		// text.setGravity(Gravity.CENTER);
		// return text;

		// 负值的
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);
		// 初始化自定义空间TabPageIndicator
		mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		ViewUtils.inject(this, view);
		return view;
	}

	/**
	 * 给导航栏上的箭头加一个跳到下一个导航页的点击事件
	 */
	@OnClick(R.id.btn_next)
	public void nextPage(View view) {
		int currentItem = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++currentItem);
	}

	@Override
	public void initData() {
		mPagerList = new ArrayList<TabDetailPager>();

		// 初始化页签数据
		for (int i = 0; i < mNewsTabDatas.size(); i++) {
			TabDetailPager pager = new TabDetailPager(mActivity,
					mNewsTabDatas.get(i));
			mPagerList.add(pager);
		}

		mViewPager.setAdapter(new MenuDetailAdapter());
		/**
		 * 将viewpager和mIndicator关联起来 必须在给ViewPager设置适配器之后，才可以设置viewPager
		 */
		mIndicator.setViewPager(mViewPager);
		/**
		 * 当TabPageIndicator和ViewPager绑定，则设置监听的时候，要给TabPageIndicator设置监听
		 */
		mIndicator.setOnPageChangeListener(this);
	}

	/**
	 * 这是第二层ViewPager的适配器
	 */
	class MenuDetailAdapter extends PagerAdapter {

		/**
		 * 仿照引用自定义控件写的方法
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return mNewsTabDatas.get(position).title;
		}

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TabDetailPager pager = mPagerList.get(position);
			container.addView(pager.mRootView);
			pager.initData();
			return pager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	@Override
	public void onPageSelected(int position) {
		MainActivity mainActivity = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainActivity.getSlidingMenu();
		if (position != 0) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

}
