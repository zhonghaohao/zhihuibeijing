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
	 * 开始测试用的，已经没用
	 */
	// private TextView tvText;

	/**
	 * NewTabData的数据
	 */
	private NewsTabData mTabData;

	/**
	 * 请求TaData数据的地址
	 */
	private String mUrl;
	/**
	 * TabData的数据
	 */
	private TabData mTabDetailData;

	/**
	 * tab_detail_pager中用于显示头条新闻图片ViewPager标签
	 */
	@ViewInject(R.id.vp_news)
	private ViewPager mViewPager;
	/**
	 * 头条新闻的底部标题
	 */
	@ViewInject(R.id.tv_title)
	private TextView tvTitle;
	/**
	 * 头条新闻的适配器
	 */
	private TopNewsAdapter mTopNewsAdapter;
	/**
	 * 头条新闻数据集合
	 */
	private ArrayList<TopNewsData> mTopNewsList;
	/**
	 * tab_detail_pager中的标签 头条新闻位置指示器
	 */
	@ViewInject(R.id.indicator)
	private CirclePageIndicator mIndicator;

	/**
	 * listView新闻列表
	 */
	@ViewInject(R.id.lv_list)
	private RefreshListView lvList;
	/**
	 * listView新闻数据集合
	 */
	private ArrayList<TabNewsData> mNewsList;
	/**
	 * 上拉刷新加载更多页面的地址
	 */
	private String mMoreUrl;
	/**
	 * 新闻的适配器
	 */
	private NewsAdapter mNewsAdapter;

	/**
	 * 用于轮播头条新闻图片
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
		// tvText.setText("页签详情页");
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
		 * 把第三层ViewPager作为第二层ListView的一个头，以解决第三层ViewPager可以跟着 listView的滑动向上滑动
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
					Toast.makeText(mActivity, "最后一页了", Toast.LENGTH_SHORT)
							.show();
					lvList.onRefreshComplete(false);// 收起加载更多的布局
				}
			}
		});
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("position: " + position);
				// 35311,34221,34234,34342
				// 在本地记录已读状态
				String ids = SPUtils.getString(mActivity, "read_ids", "");
				String readId = mNewsList.get(position).id;
				if (!ids.contains(readId)) {
					ids = ids + readId + ",";
					SPUtils.setString(mActivity, "read_ids", ids);
				}

				/**
				 * mNewsAdapter.notifyDataSetChanged(); 实现局部界面刷新,
				 * 这个view就是被点击的item布局对象
				 */
				changeReadState(view);
				/**
				 * 启动Activity
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
	 * 局部改变新闻的Item颜色
	 */
	public void changeReadState(View view) {
		TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
		tvTitle.setTextColor(Color.GRAY);
	}

	/**
	 * 初始化导航栏数据
	 */
	@Override
	public void initData() {
		// tvText.setText(mTabData.title);
		// 读取缓存
		String cache = CacheUtils.getCache(mUrl, mActivity);
		if (!TextUtils.isEmpty(cache)) {
			parseData(cache, false);
		}
		getDataFromServer();
	}

	/**
	 * 从服务器获取不同导航栏的数据
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = (String) responseInfo.result;
				System.out.println("页签详情页返回结果:" + result);
				Toast.makeText(mActivity, "页签详情页请求成功", Toast.LENGTH_SHORT)
						.show();

				parseData(result, false);
				lvList.onRefreshComplete(true);
				// 设置缓存
				CacheUtils.setCache(mUrl, result, mActivity);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, "页签详情页请求失败", Toast.LENGTH_SHORT)
						.show();
				error.printStackTrace();
				lvList.onRefreshComplete(false);
			}
		});
	}

	/**
	 * 上拉加载更多数据
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
	 * 解析服务器返回的数据
	 * 
	 * @param result
	 */
	protected void parseData(String result, Boolean isMore) {
		Gson gson = new Gson();
		mTabDetailData = gson.fromJson(result, TabData.class);
		// System.out.println("页签详情解析:" + mTabDetailData);
		/**
		 * 头条新闻
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
			 * listView的新闻
			 */
			mNewsList = mTabDetailData.data.news;
			if (mTopNewsList != null) {
				mTopNewsAdapter = new TopNewsAdapter();
				mViewPager.setAdapter(mTopNewsAdapter);
				/**
				 * 给ViewPager设置指示器 要ViewPager设置Adapter之后才可以设置
				 */
				mIndicator.setViewPager(mViewPager);
				/**
				 * 设置快照，即点是跳跃的，而不是连续移动的
				 */
				mIndicator.setSnap(true);
				/**
				 * 为了使同一个导航栏在滑动的时候，文字可以变化
				 * 注意mIndicator.setViewPager(mViewPager);与
				 * mViewPager.setOnPageChangeListener(this);
				 * 使用的顺序，应该先使用前者，否则红点可能不会动
				 */
				// mIndicator.setOnPageChangeListener(this);
				/**
				 * 解决导航栏的数据被销毁时，红点的位子被记录，而从新加载导航栏的
				 * 某个导航栏数据的时候，红点会出现在被销毁时候的位置，而我们需要它出 现在第一个位子
				 */
				mIndicator.onPageSelected(0);
				tvTitle.setText(mTopNewsList.get(0).title);
			}
			if (mNewsList != null) {
				mNewsAdapter = new NewsAdapter();
				lvList.setAdapter(mNewsAdapter);

			}
			/**
			 * 轮播头条新闻图片
			 */
			if(handler == null){//加判断是为了避免重复创建hander对象
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
						//为了避免轮播的事件不对
						handler.removeCallbacksAndMessages(null);
						handler.sendEmptyMessageDelayed(0, 3000);
					}
				};
			}
			handler.sendEmptyMessageDelayed(0, 3000);

		} else {
			// 如果是加载下一页,需要将数据追加给原来的集合
			ArrayList<TabNewsData> news = mTabDetailData.data.news;
			mNewsList.addAll(news);
			mNewsAdapter.notifyDataSetChanged();
		}

	}

	/**
	 * 头条新闻图片展示的适配器
	 */
	class TopNewsAdapter extends PagerAdapter {
		BitmapUtils utils;

		public TopNewsAdapter() {
			utils = new BitmapUtils(mActivity);
			/**
			 * 设置当请求图片时显示本地默认图片
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
			 * 给头条新闻图片设置侦听，当触摸时停止轮播
			 */
			image.setOnTouchListener(new MyOnTouchListener());
			// 设置失陪屏幕
			image.setScaleType(ScaleType.FIT_XY);
			/**
			 * 第一个参数表示容器，第二个参数是url地址
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
			 * 设置当请求图片时显示本地默认图片
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
				System.out.println("按下");
				handler.removeCallbacksAndMessages(null);// 删除Handler中的所有消息发送的消息，用来停止轮播
				// mHandler.postDelayed(new Runnable() {//可以用此方法，在主线程中发延迟消息
				//
				// @Override
				// public void run() {
				//
				// }
				// }, 3000);
				break;
			case MotionEvent.ACTION_CANCEL:
				System.out.println("事件取消");
				handler.sendEmptyMessageDelayed(0, 3000);
				break;
			case MotionEvent.ACTION_UP:
				System.out.println("抬起");
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
