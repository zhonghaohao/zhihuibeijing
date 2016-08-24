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
	 * ��ҳ�ײ������tab��ViewGroup
	 */
	@ViewInject(R.id.rg_group)
	private RadioGroup rgGroup;
	/**
	 * ��ҳ��ViewPager
	 */
	@ViewInject(R.id.vp_content)
	private ViewPager mViewPager;

	/**
	 * ���ڴ�Viewpager��Ҫչʾ������
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
		 * Ĭ��ѡ��չʾhomeҳ�棬�˴�������ΪĬ��չʾ��һҳ���ݣ�����Ϊ��
		 * �õײ�tab��ҳ��ɫ
		 */
		rgGroup.check(R.id.rb_home);
		
		
		
		
		mPagerList = new ArrayList<BasePager>();
		// ��ʼ��5����ҳ��
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
		 * ���ײ���Radio���õ������
		 */
		rgGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					System.out.println("�е���¼�");
					/**
					 * mViewPager.setCurrentItem(0);�����һ��������ViewPager��Item false
					 * : ��ʾȥ���仯�Ķ�������Ч��
					 */
					mViewPager.setCurrentItem(0, false);// ȥ���л�ҳ��Ķ���
					break;
				case R.id.rb_news:
					mViewPager.setCurrentItem(1, false);// ���õ�ǰҳ��
					break;
				case R.id.rb_smart:
					mViewPager.setCurrentItem(2, false);// ���õ�ǰҳ��
					break;
				case R.id.rb_gov:
					mViewPager.setCurrentItem(3, false);// ���õ�ǰҳ��
					break;
				case R.id.rb_setting:
					mViewPager.setCurrentItem(4, false);// ���õ�ǰҳ��
					break;

				default:
					break;
				}
			}
		});
		
		/**
		 * 1: Ϊ�˿˷�ViewPagerԤ���ػ��ƣ��Ӷ�ʹ���˷����������Բ��������������������
		 * ������ѡ������һ��ҳ���ʱ���ټ�������
		 * 2: ע����Ϊ��ѡ��֮���ڼ������ݣ����Զ��ڸս����ǲ��ܼ���Ĭ�ϵ�һҳ,���Ըս���ʱ��
		 * ��һҳҪ�Լ����⴦��(һ���뱾ҳ��ͼ��ص�һҳ)
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
		
		//һ����������ҳ��Ĭ���ȼ��ص�һҳ
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
			 *  ��ʼ������.... ��Ҫ���ڴ˴���ʼ������, �����Ԥ������һ��ҳ�棬
			 *  ʹ�ö�����ҳ���������õĲ�����ͨ�������򿪲���˵������趨ʧЧ
			 *  ͬʱҲ����ΪƵ��Ԥ���ض��˷�����
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
