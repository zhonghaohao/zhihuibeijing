package com.heima.capter.fragment;

import java.util.ArrayList;
import java.util.List;

import com.heima.capter.R;
import com.heima.capter.base.BasePager;
import com.heima.capter.base.impl.GovAffairsPager;
import com.heima.capter.base.impl.HomePager;
import com.heima.capter.base.impl.NewsCenterPager;
import com.heima.capter.base.impl.SettingPager;
import com.heima.capter.base.impl.SmartServicePager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ContentFragment extends BaseFragment {

	/**
	 * 主页底部的五个tab的ViewGroup
	 */
	@ViewInject(R.id.rg_group)
	private RadioGroup rgGroup;
	/**
	 * 主页的ViewPager
	 */
	@ViewInject(R.id.vp_content)
	private ViewPager mViewPager;

	/**
	 * 用于存Viewpager所要展示的内容
	 */
	private List<BasePager> mPagerList;

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		/**
		 * 默认选择展示home页面，此处并不是为默认展示第一页数据，而是为了
		 * 让底部tab首页变色
		 */
		rgGroup.check(R.id.rb_home);
		
		
		
		
		mPagerList = new ArrayList<BasePager>();
		// 初始化5个子页面
		// for (int i = 0; i < 5; i++) {
		// BasePager pager = new BasePager(mActivity);
		// mPagerList.add(pager);
		// }
		mPagerList.add(new HomePager(mActivity));
		mPagerList.add(new NewsCenterPager(mActivity));
		mPagerList.add(new SmartServicePager(mActivity));
		mPagerList.add(new GovAffairsPager(mActivity));
		mPagerList.add(new SettingPager(mActivity));

		mViewPager.setAdapter(new ContentAdapter());
		
		
		/**
		 * 给底部的Radio设置点击侦听
		 */
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					System.out.println("有点击事件");
					/**
					 * mViewPager.setCurrentItem(0);点击哪一个就设置ViewPager的Item false
					 * : 表示去掉变化的动画渐变效果
					 */
					mViewPager.setCurrentItem(0, false);// 去掉切换页面的动画
					break;
				case R.id.rb_news:
					mViewPager.setCurrentItem(1, false);// 设置当前页面
					break;
				case R.id.rb_smart:
					mViewPager.setCurrentItem(2, false);// 设置当前页面
					break;
				case R.id.rb_gov:
					mViewPager.setCurrentItem(3, false);// 设置当前页面
					break;
				case R.id.rb_setting:
					mViewPager.setCurrentItem(4, false);// 设置当前页面
					break;

				default:
					break;
				}
			}
		});
		
		/**
		 * 1: 为了克服ViewPager预加载机制，从而使得浪费流量，所以不在适配器里面填充数据
		 * 而是在选定了哪一个页面的时候再加载数据
		 * 2: 注意因为是选定之后在加载数据，所以对于刚进入是不能加载默认第一页,所以刚进入时，
		 * 第一页要自己特殊处理(一进入本页面就加载第一页)
		 */
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				mPagerList.get(position).initData();
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		
		//一进入内容主页就默认先加载第一页
				mPagerList.get(0).initData();
	}

	class ContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mPagerList.get(position);
			container.addView(pager.mRootView);
			/**
			 *  初始化数据.... 不要放在此处初始化数据, 否则会预加载下一个页面，
			 *  使得对于首页和设置设置的不可以通过滑动打开侧面菜单栏的设定失效
			 *  同时也会因为频繁预加载而浪费流量
			 */
//			pager.initData();
			return pager.mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	public NewsCenterPager getNewsCenterPager() {
		return (NewsCenterPager) mPagerList.get(1);
	}

}
