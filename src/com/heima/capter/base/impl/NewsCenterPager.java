package com.heima.capter.base.impl;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heima.capter.activity.MainActivity;
import com.heima.capter.base.BaseMenuDetailPager;
import com.heima.capter.base.BasePager;
import com.heima.capter.base.menudetail.InteractMenuDetailPager;
import com.heima.capter.base.menudetail.NewsMenuDetailPager;
import com.heima.capter.base.menudetail.PhotoMenuDetailPager;
import com.heima.capter.base.menudetail.TopicMenuDetailPager;
import com.heima.capter.domain.NewsData;
import com.heima.capter.domain.NewsData.NewsMenuData;
import com.heima.capter.fragment.LeftMenuFragment;
import com.heima.capter.global.GlobalContants;
import com.heima.capter.utils.CacheUtils;
import com.heima.capter.utils.SPUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 新闻中心
 * 
 * @author Kevin
 * 
 */
public class NewsCenterPager extends BasePager {
	/**
	 * 四个菜单详情界面的集合
	 */
	private List<BaseMenuDetailPager> mPagers;
	/**
	 * 最外层的类----Xml的根节点
	 */
	private NewsData  mNewsData;

	public NewsCenterPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		/**
		 * 用于测试看ViewPager是否预加载
		 */
		System.out.println("初始化新闻中心数据....");

		tvTitle.setText("新闻");
		/**
		 *  显示侧边栏菜单按钮
		 */
		btnMenu.setVisibility(View.VISIBLE);
		/**
		 *  设置是否可以打开侧边栏
		 */
		setSlidingMenuEnable(true);
		String cache = CacheUtils.getCache(GlobalContants.CATEGORIES_URL, mActivity);
		if(!TextUtils.isEmpty(cache)){
			parseData(cache);
		}
		/**
		 * 从服务器拿到数据向FrameLayout中动态添加布局
		 * 注意不论是否有缓存都要请求服务器数据，因为服务器可能有更新内容
		 * 缓存是为了增强用户体验
		 */
		getDataFromServer();
	}

	/**
	 * 从服务器获取数据
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();

		/**
		 *  使用xutils发送请求
		 *  此处的泛型String要根据请求返回的内容设置，有可能是个文件等等
		 */
		utils.send(HttpMethod.GET, GlobalContants.CATEGORIES_URL,
				new RequestCallBack<String>() {

					// 访问成功
					@Override
					public void onSuccess(ResponseInfo responseInfo) {
						/**
						 * 获取服务器返回的内容
						 */
						String result = (String) responseInfo.result;
//						System.out.println("返回结果:" + result);
//						System.out.println("成功请求数据");
						Toast.makeText(mActivity, "请求数据成功", Toast.LENGTH_SHORT)
						.show();

						parseData(result);
						CacheUtils.setCache(GlobalContants.CATEGORIES_URL, result, mActivity);
					}

					// 访问失败
					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, "请求数据失败", Toast.LENGTH_SHORT)
								.show();
//						System.out.println("请求数据失败");
						error.printStackTrace();
					}

				});
	}

	/**
	 * 解析网络数据，对请求到的数据进行class处理
	 * 
	 * @param result 是json文件
	 */
	protected void parseData(String result) {
		Gson gson = new Gson();
		mNewsData = gson.fromJson(result, NewsData.class);
//		System.out.println("解析结果:" + mNewsData);

		/**
		 *  刷新测边栏的数据
		 */
		MainActivity mainUi = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
		/**
		 * 这个方法在leftMenuFragment类中，用于设置显示侧边栏listView数据
		 */
		leftMenuFragment.setMenuData(mNewsData);

		// 准备4个菜单详情页
		mPagers = new ArrayList<BaseMenuDetailPager>();
		mPagers.add(new NewsMenuDetailPager(mActivity,
				mNewsData.data.get(0).children));
		mPagers.add(new TopicMenuDetailPager(mActivity));
		mPagers.add(new PhotoMenuDetailPager(mActivity));
		mPagers.add(new InteractMenuDetailPager(mActivity));
		
		/**
		 * 设置主菜单详情页-侧滑菜单的新闻为主菜单的默认当前页
		 */
		setCurrentMenuDetailPager(0);
	}

	/**
	 * 根据侧边菜单的点击选择切换主菜单中Viewpager中部的信息
	 * @param position  侧边被点击的Item
	 */
	public void setCurrentMenuDetailPager(int position) {
		BaseMenuDetailPager pager = mPagers.get(position);// 获取当前要显示的菜单详情页
		flContent.removeAllViews();// 清除之前的布局
		/**
		 * 将菜单详情页的布局设置给帧布局
		 * pager.mRootView这个mRootView在子类NewsMenuDetailPager等四个页面实现
		 * 而子类在被添加的时候(自定义子类的时候),子类已经实现了initView()方法
		 * 所以mRootView总是已经初始化好的
		 */
		flContent.addView(pager.mRootView);

		/**
		 *  设置当前页最上方的标题(title),覆盖刚开始做时候写的固定标题
		 */
		NewsMenuData menuData = mNewsData.data.get(position);
		tvTitle.setText(menuData.title);
		/**
		 *  初始化当前页面的数据(即通过调用父类BaseMenuDetailPager的initData()间接调用子类
		 *  NewsMenuDetailPager等的initData()方法，给NewsMenuDetailPager等子类根据
		 *  各自不同需求实现)
		 */
		pager.initData();
	}

}

