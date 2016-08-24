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
 * �˵�����ҳ-����
 * 
 * @author Kevin
 * 
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements
		OnPageChangeListener {

	/**
	 * 11��������������(mNewsTabDatas��һ���������children����)
	 */
	public List<NewsTabData> mNewsTabDatas;
	/**
	 * viewpager
	 */
	private ViewPager mViewPager;
	/**
	 * ���ڴ洢���������ݣ�����������ViewPager��list����
	 */
	private List<TabDetailPager> mPagerList;
	private TabPageIndicator mIndicator;

	/**
	 * ������췽����Ϊ�˰ѵ����������ݴ�����
	 * 
	 * @param activity
	 * @param children
	 *            ���е��������ݵ�������
	 */
	public NewsMenuDetailPager(Activity activity, List<NewsTabData> children) {
		super(activity);
		mNewsTabDatas = children;
	}

	@Override
	public View initViews() {
		// ��д�ĺ���Ҫɾ��
		// TextView text = new TextView(mActivity);
		// text.setText("�˵�����ҳ-ר��");
		// text.setTextColor(Color.RED);
		// text.setTextSize(25);
		// text.setGravity(Gravity.CENTER);
		// return text;

		// ��ֵ��
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);
		// ��ʼ���Զ���ռ�TabPageIndicator
		mIndicator = (TabPageIndicator) view.findViewById(R.id.indicator);
		ViewUtils.inject(this, view);
		return view;
	}

	/**
	 * ���������ϵļ�ͷ��һ��������һ������ҳ�ĵ���¼�
	 */
	@OnClick(R.id.btn_next)
	public void nextPage(View view) {
		int currentItem = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++currentItem);
	}

	@Override
	public void initData() {
		mPagerList = new ArrayList<TabDetailPager>();

		// ��ʼ��ҳǩ����
		for (int i = 0; i < mNewsTabDatas.size(); i++) {
			TabDetailPager pager = new TabDetailPager(mActivity,
					mNewsTabDatas.get(i));
			mPagerList.add(pager);
		}

		mViewPager.setAdapter(new MenuDetailAdapter());
		/**
		 * ��viewpager��mIndicator�������� �����ڸ�ViewPager����������֮�󣬲ſ�������viewPager
		 */
		mIndicator.setViewPager(mViewPager);
		/**
		 * ��TabPageIndicator��ViewPager�󶨣������ü�����ʱ��Ҫ��TabPageIndicator���ü���
		 */
		mIndicator.setOnPageChangeListener(this);
	}

	/**
	 * ���ǵڶ���ViewPager��������
	 */
	class MenuDetailAdapter extends PagerAdapter {

		/**
		 * ���������Զ���ؼ�д�ķ���
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
