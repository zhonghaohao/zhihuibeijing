package com.heima.capter.activity;

import java.util.ArrayList;
import java.util.List;

import com.heima.capter.R;
import com.heima.capter.utils.SPUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class GuideActivity extends Activity {
	/**
	 * ViewPagerID
	 */
	private ViewPager vp_guide;
	/**
	 * 进入主页面的按钮
	 */
	private Button btn_start;
	/**
	 * 用于绘制三个小圆点的linearLayout布局ID
	 */
	private LinearLayout ll_point_group;
	/**
	 * 用于绘制红点的ViewID
	 */
	private View view_red_point;
	/**
	 * ViewPager的适配器
	 */
	private GuidePagerAdapter adapter;
	/**
	 * 用于添加ViewPager页面所需要的资源(元素的资源的ResourceID)
	 */
	private List<ImageView> list;
	/**
	 * 保存两个圆点之间的距离
	 */
	private int mPointWidth;
	
	private static final int[] mImageIds = new int[] { R.drawable.guide_1,
		R.drawable.guide_2, R.drawable.guide_3 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);

		initUI();
		initViews();
	}

	/**
	 * 初始化ID及设置适配器
	 */
	private void initUI() {
		vp_guide = (ViewPager) findViewById(R.id.vp_guide);
		btn_start = (Button) findViewById(R.id.btn_start);
		ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
		view_red_point = findViewById(R.id.view_red_point);
		btn_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SPUtils.setBoolean(GuideActivity.this, "is_user_guide_showed", true);
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
				finish();
			}
		});
	}
	/**
	 * 初始化View(三个圆点)
	 */
	private void initViews() {
		list = new ArrayList<ImageView>();
		//初始化三个ViewPager的图片
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView imageView = new ImageView(this);
			//注意给圆点设置背景资源
			imageView.setBackgroundResource(mImageIds[i]);
			list.add(imageView);
		}
		//初始化引导也三个圆点
		for (int i = 0; i < mImageIds.length; i++) {
			View point = new View(this);
			//注意给圆点设置背景资源---此处是一个shape
			point.setBackgroundResource(R.drawable.shape_point_gray);
			LayoutParams params = new LayoutParams(10, 10);
			if(i > 0){
				//两种方法都可以，第二种更好
//				params.setMargins(10, 0, 0, 0);
				params.leftMargin = 10;
			}
			point.setLayoutParams(params);
			ll_point_group.addView(point);
		}
		/**
		 * 为了可以得到三个圆点的每个圆点之间的间距----直接获取返回的为0
		 */
		ll_point_group.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				ll_point_group.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				mPointWidth = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
//				System.out.println("pointWidth=" + pointWidth);
			}
		});
		adapter = new GuidePagerAdapter();
		vp_guide.setAdapter(adapter);
		addListenerForViewPager();
	}
	/**
	 * 给ViewPager设置滑动监听,为了可以控制红点的移动
	 */
	private void addListenerForViewPager() {
		vp_guide.setOnPageChangeListener(new OnPageChangeListener() {
			
			/**
			 * 正在滑动的页面
			 * @param position	当前滑动的是第几页
			 * @param positionOffset	当前滑动的偏移比例(每页都是0--100%)
			 * @param positionOffsetPixels	在每个页面滑动的距离
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
//				System.out.println("position=" +position + "positionOffset=" + positionOffset + "positionOffsetPixels=" + positionOffsetPixels);
				int redPointWidth = (int) (mPointWidth * positionOffset) + position * mPointWidth;
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view_red_point.getLayoutParams();
				//以下两种方法都可以，只是第二种比较好，因为第一种有的时候不知道要其他的margin要设置为多少
//				layoutParams.setMargins(redPointWidth, 0, 0, 0);
				layoutParams.leftMargin = redPointWidth;
				view_red_point.setLayoutParams(layoutParams);
			}
			/**
			 * 当前是第几个页面
			 * @param position
			 */
			@Override
			public void onPageSelected(int position) {
				if(position == list.size() - 1 ){
					btn_start.setVisibility(View.VISIBLE);
				}else{
					btn_start.setVisibility(View.INVISIBLE);
				}
			}
			/**
			 * 滑动状态改变
			 * @param state 惯性，闲置， 滑动
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}
	/**
	 * 引导页Adapter的适配器
	 * @author Administrator
	 */
	class GuidePagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(list.get(position));
			return list.get(position);
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
	}
}
