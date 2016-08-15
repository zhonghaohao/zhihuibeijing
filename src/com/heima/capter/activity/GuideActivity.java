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
	 * ������ҳ��İ�ť
	 */
	private Button btn_start;
	/**
	 * ���ڻ�������СԲ���linearLayout����ID
	 */
	private LinearLayout ll_point_group;
	/**
	 * ���ڻ��ƺ���ViewID
	 */
	private View view_red_point;
	/**
	 * ViewPager��������
	 */
	private GuidePagerAdapter adapter;
	/**
	 * �������ViewPagerҳ������Ҫ����Դ(Ԫ�ص���Դ��ResourceID)
	 */
	private List<ImageView> list;
	/**
	 * ��������Բ��֮��ľ���
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
	 * ��ʼ��ID������������
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
	 * ��ʼ��View(����Բ��)
	 */
	private void initViews() {
		list = new ArrayList<ImageView>();
		//��ʼ������ViewPager��ͼƬ
		for (int i = 0; i < mImageIds.length; i++) {
			ImageView imageView = new ImageView(this);
			//ע���Բ�����ñ�����Դ
			imageView.setBackgroundResource(mImageIds[i]);
			list.add(imageView);
		}
		//��ʼ������Ҳ����Բ��
		for (int i = 0; i < mImageIds.length; i++) {
			View point = new View(this);
			//ע���Բ�����ñ�����Դ---�˴���һ��shape
			point.setBackgroundResource(R.drawable.shape_point_gray);
			LayoutParams params = new LayoutParams(10, 10);
			if(i > 0){
				//���ַ��������ԣ��ڶ��ָ���
//				params.setMargins(10, 0, 0, 0);
				params.leftMargin = 10;
			}
			point.setLayoutParams(params);
			ll_point_group.addView(point);
		}
		/**
		 * Ϊ�˿��Եõ�����Բ���ÿ��Բ��֮��ļ��----ֱ�ӻ�ȡ���ص�Ϊ0
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
	 * ��ViewPager���û�������,Ϊ�˿��Կ��ƺ����ƶ�
	 */
	private void addListenerForViewPager() {
		vp_guide.setOnPageChangeListener(new OnPageChangeListener() {
			
			/**
			 * ���ڻ�����ҳ��
			 * @param position	��ǰ�������ǵڼ�ҳ
			 * @param positionOffset	��ǰ������ƫ�Ʊ���(ÿҳ����0--100%)
			 * @param positionOffsetPixels	��ÿ��ҳ�滬���ľ���
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
//				System.out.println("position=" +position + "positionOffset=" + positionOffset + "positionOffsetPixels=" + positionOffsetPixels);
				int redPointWidth = (int) (mPointWidth * positionOffset) + position * mPointWidth;
				RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view_red_point.getLayoutParams();
				//�������ַ��������ԣ�ֻ�ǵڶ��ֱȽϺã���Ϊ��һ���е�ʱ��֪��Ҫ������marginҪ����Ϊ����
//				layoutParams.setMargins(redPointWidth, 0, 0, 0);
				layoutParams.leftMargin = redPointWidth;
				view_red_point.setLayoutParams(layoutParams);
			}
			/**
			 * ��ǰ�ǵڼ���ҳ��
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
			 * ����״̬�ı�
			 * @param state ���ԣ����ã� ����
			 */
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}
	/**
	 * ����ҳAdapter��������
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
