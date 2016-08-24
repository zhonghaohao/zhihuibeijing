package com.heima.capter.base;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.heima.capter.R;
import com.heima.capter.activity.NewsDetailActivity;
import com.heima.capter.domain.NewsData.NewsTabData;
import com.heima.capter.domain.TabData;
import com.heima.capter.domain.TabData.TabNewsData;
import com.heima.capter.domain.TabData.TopNewsData;
import com.heima.capter.global.GlobalContants;
import com.heima.capter.utils.CacheUtils;
import com.heima.capter.utils.SPUtils;
import com.heima.capter.view.RefreshListView;
import com.heima.capter.view.RefreshListView.OnRefreshListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TabDetailPager extends BaseMenuDetailPager implements
		OnPageChangeListener {
	/**
	 * ��ʼ�����õģ��Ѿ�û��
	 */
	// private TextView tvText;

	/**
	 * NewTabData������
	 */
	private NewsTabData mTabData;

	/**
	 * ����TaData���ݵĵ�ַ
	 */
	private String mUrl;
	/**
	 * TabData������
	 */
	private TabData mTabDetailData;

	/**
	 * tab_detail_pager��������ʾͷ������ͼƬViewPager��ǩ
	 */
	@ViewInject(R.id.vp_news)
	private ViewPager mViewPager;
	/**
	 * ͷ�����ŵĵײ�����
	 */
	@ViewInject(R.id.tv_title)
	private TextView tvTitle;
	/**
	 * ͷ�����ŵ�������
	 */
	private TopNewsAdapter mTopNewsAdapter;
	/**
	 * ͷ���������ݼ���
	 */
	private ArrayList<TopNewsData> mTopNewsList;
	/**
	 * tab_detail_pager�еı�ǩ ͷ������λ��ָʾ��
	 */
	@ViewInject(R.id.indicator)
	private CirclePageIndicator mIndicator;

	/**
	 * listView�����б�
	 */
	@ViewInject(R.id.lv_list)
	private RefreshListView lvList;
	/**
	 * listView�������ݼ���
	 */
	private ArrayList<TabNewsData> mNewsList;
	/**
	 * ����ˢ�¼��ظ���ҳ��ĵ�ַ
	 */
	private String mMoreUrl;
	/**
	 * ���ŵ�������
	 */
	private NewsAdapter mNewsAdapter;

	/**
	 * �����ֲ�ͷ������ͼƬ
	 */
	private Handler handler;

	public TabDetailPager(Activity activity, NewsTabData newsTabData) {
		super(activity);
		mTabData = newsTabData;
		mUrl = GlobalContants.string + mTabData.url;
	}

	@Override
	public View initViews() {
		// tvText = new TextView(mActivity);
		// tvText.setText("ҳǩ����ҳ");
		// tvText.setTextColor(Color.RED);
		// tvText.setTextSize(25);
		// tvText.setGravity(Gravity.CENTER);
		// return tvText;

		View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
		View headerView = View.inflate(mActivity, R.layout.list_header_topnews,
				null);
		ViewUtils.inject(this, view);
		ViewUtils.inject(this, headerView);

		/**
		 * �ѵ�����ViewPager��Ϊ�ڶ���ListView��һ��ͷ���Խ��������ViewPager���Ը��� listView�Ļ������ϻ���
		 */
		lvList.addHeaderView(headerView);
		lvList.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				getDataFromServer();
			}

			@Override
			public void onLoadMore() {
				if (mMoreUrl != null) {
					getMoreDataFromServer();
				} else {
					Toast.makeText(mActivity, "���һҳ��", Toast.LENGTH_SHORT)
							.show();
					lvList.onRefreshComplete(false);// ������ظ���Ĳ���
				}
			}
		});
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("position: " + position);
				// 35311,34221,34234,34342
				// �ڱ��ؼ�¼�Ѷ�״̬
				String ids = SPUtils.getString(mActivity, "read_ids", "");
				String readId = mNewsList.get(position).id;
				if (!ids.contains(readId)) {
					ids = ids + readId + ",";
					SPUtils.setString(mActivity, "read_ids", ids);
				}

				/**
				 * mNewsAdapter.notifyDataSetChanged(); ʵ�־ֲ�����ˢ��,
				 * ���view���Ǳ������item���ֶ���
				 */
				changeReadState(view);
				/**
				 * ����Activity
				 */
				Intent intent = new Intent(mActivity, NewsDetailActivity.class);
				intent.putExtra("url", mNewsList.get(position).url);
				mActivity.startActivity(intent);
			}

		});
		mViewPager.setOnPageChangeListener(this);
		return view;
	}

	/**
	 * �ֲ��ı����ŵ�Item��ɫ
	 */
	public void changeReadState(View view) {
		TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
		tvTitle.setTextColor(Color.GRAY);
	}

	/**
	 * ��ʼ������������
	 */
	@Override
	public void initData() {
		// tvText.setText(mTabData.title);
		// ��ȡ����
		String cache = CacheUtils.getCache(mUrl, mActivity);
		if (!TextUtils.isEmpty(cache)) {
			parseData(cache, false);
		}
		getDataFromServer();
	}

	/**
	 * �ӷ�������ȡ��ͬ������������
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = (String) responseInfo.result;
				System.out.println("ҳǩ����ҳ���ؽ��:" + result);
				Toast.makeText(mActivity, "ҳǩ����ҳ����ɹ�", Toast.LENGTH_SHORT)
						.show();

				parseData(result, false);
				lvList.onRefreshComplete(true);
				// ���û���
				CacheUtils.setCache(mUrl, result, mActivity);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, "ҳǩ����ҳ����ʧ��", Toast.LENGTH_SHORT)
						.show();
				error.printStackTrace();
				lvList.onRefreshComplete(false);
			}
		});
	}

	/**
	 * �������ظ�������
	 */
	private void getMoreDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = (String) responseInfo.result;

				parseData(result, true);
				lvList.onRefreshComplete(true);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				error.printStackTrace();
				lvList.onRefreshComplete(false);
			}
		});
	}

	/**
	 * 
	 * �������������ص�����
	 * 
	 * @param result
	 */
	protected void parseData(String result, Boolean isMore) {
		Gson gson = new Gson();
		mTabDetailData = gson.fromJson(result, TabData.class);
		// System.out.println("ҳǩ�������:" + mTabDetailData);
		/**
		 * ͷ������
		 */
		mTopNewsList = mTabDetailData.data.topnews;
		String More = mTabDetailData.data.more;
		if (!TextUtils.isEmpty(More)) {
			mMoreUrl = GlobalContants.string + More;
		} else {
			mMoreUrl = null;
		}
		if (!isMore) {
			/**
			 * listView������
			 */
			mNewsList = mTabDetailData.data.news;
			if (mTopNewsList != null) {
				mTopNewsAdapter = new TopNewsAdapter();
				mViewPager.setAdapter(mTopNewsAdapter);
				/**
				 * ��ViewPager����ָʾ�� ҪViewPager����Adapter֮��ſ�������
				 */
				mIndicator.setViewPager(mViewPager);
				/**
				 * ���ÿ��գ���������Ծ�ģ������������ƶ���
				 */
				mIndicator.setSnap(true);
				/**
				 * Ϊ��ʹͬһ���������ڻ�����ʱ�����ֿ��Ա仯
				 * ע��mIndicator.setViewPager(mViewPager);��
				 * mViewPager.setOnPageChangeListener(this);
				 * ʹ�õ�˳��Ӧ����ʹ��ǰ�ߣ���������ܲ��ᶯ
				 */
				// mIndicator.setOnPageChangeListener(this);
				/**
				 * ��������������ݱ�����ʱ������λ�ӱ���¼�������¼��ص�������
				 * ĳ�����������ݵ�ʱ�򣬺�������ڱ�����ʱ���λ�ã���������Ҫ���� ���ڵ�һ��λ��
				 */
				mIndicator.onPageSelected(0);
				tvTitle.setText(mTopNewsList.get(0).title);
			}
			if (mNewsList != null) {
				mNewsAdapter = new NewsAdapter();
				lvList.setAdapter(mNewsAdapter);

			}
			/**
			 * �ֲ�ͷ������ͼƬ
			 */
			if(handler == null){//���ж���Ϊ�˱����ظ�����hander����
				handler = new Handler(){
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						int currentItem = mViewPager.getCurrentItem();
						if(currentItem < mTopNewsList.size() - 1){
							currentItem ++;
						}else{
							currentItem = 0;
						}
						mViewPager.setCurrentItem(currentItem);
						//Ϊ�˱����ֲ����¼�����
						handler.removeCallbacksAndMessages(null);
						handler.sendEmptyMessageDelayed(0, 3000);
					}
				};
			}
			handler.sendEmptyMessageDelayed(0, 3000);

		} else {
			// ����Ǽ�����һҳ,��Ҫ������׷�Ӹ�ԭ���ļ���
			ArrayList<TabNewsData> news = mTabDetailData.data.news;
			mNewsList.addAll(news);
			mNewsAdapter.notifyDataSetChanged();
		}

	}

	/**
	 * ͷ������ͼƬչʾ��������
	 */
	class TopNewsAdapter extends PagerAdapter {
		BitmapUtils utils;

		public TopNewsAdapter() {
			utils = new BitmapUtils(mActivity);
			/**
			 * ���õ�����ͼƬʱ��ʾ����Ĭ��ͼƬ
			 */
			utils.configDefaultLoadingImage(R.drawable.topnews_item_default);
		}

		@Override
		public int getCount() {
			return mTopNewsList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView image = new ImageView(mActivity);
			/**
			 * ��ͷ������ͼƬ����������������ʱֹͣ�ֲ�
			 */
			image.setOnTouchListener(new MyOnTouchListener());
			// ����ʧ����Ļ
			image.setScaleType(ScaleType.FIT_XY);
			/**
			 * ��һ��������ʾ�������ڶ���������url��ַ
			 */
			utils.display(image, mTopNewsList.get(position).topimage);
			container.addView(image);
			return image;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	class NewsAdapter extends BaseAdapter {
		BitmapUtils utils;

		public NewsAdapter() {
			utils = new BitmapUtils(mActivity);
			/**
			 * ���õ�����ͼƬʱ��ʾ����Ĭ��ͼƬ
			 */
			utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mNewsList.size();
		}

		@Override
		public TabNewsData getItem(int position) {
			// TODO Auto-generated method stub
			return mNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				view = View.inflate(mActivity, R.layout.list_news_item, null);
				holder.iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
				holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
				holder.tv_date = (TextView) view.findViewById(R.id.tv_date);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			holder.tv_title.setText(mNewsList.get(position).title);
			holder.tv_date.setText(mNewsList.get(position).pubdate);
			utils.display(holder.iv_pic, mNewsList.get(position).listimage);
			String ids = SPUtils.getString(mActivity, "read_ids", "");
			if (ids.contains(getItem(position).id)) {
				holder.tv_title.setTextColor(Color.GRAY);
			} else {
				holder.tv_title.setTextColor(Color.BLACK);
			}
			return view;
		}

	}

	class ViewHolder {
		public ImageView iv_pic;
		public TextView tv_title;
		public TextView tv_date;
	}
	class MyOnTouchListener implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				System.out.println("����");
				handler.removeCallbacksAndMessages(null);// ɾ��Handler�е�������Ϣ���͵���Ϣ������ֹͣ�ֲ�
				// mHandler.postDelayed(new Runnable() {//�����ô˷����������߳��з��ӳ���Ϣ
				//
				// @Override
				// public void run() {
				//
				// }
				// }, 3000);
				break;
			case MotionEvent.ACTION_CANCEL:
				System.out.println("�¼�ȡ��");
				handler.sendEmptyMessageDelayed(0, 3000);
				break;
			case MotionEvent.ACTION_UP:
				System.out.println("̧��");
				handler.sendEmptyMessageDelayed(0, 3000);
				break;

			default:
				break;
			}

			return true;
		}

		
	}

	@Override
	public void onPageSelected(int position) {
		tvTitle.setText(mTopNewsList.get(position).title);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

}
