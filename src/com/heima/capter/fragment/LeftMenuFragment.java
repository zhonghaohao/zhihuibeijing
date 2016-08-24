package com.heima.capter.fragment;


import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.heima.capter.R;
import com.heima.capter.activity.MainActivity;
import com.heima.capter.base.impl.NewsCenterPager;
import com.heima.capter.domain.NewsData;
import com.heima.capter.domain.NewsData.NewsMenuData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 侧边栏
 * 
 * @author Kevin
 * 
 */
public class LeftMenuFragment extends BaseFragment {

	@ViewInject(R.id.lv_list)
	private ListView lvList;
	private List<NewsMenuData> mMenuList;
	

	private int mCurrentPos;// 当前被点击的菜单项
	private MenuAdapter mAdapter;

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		ViewUtils.inject(this, view);

		return view;
	}

	/**
	 * 给侧边栏菜单设置侦听，以便选中之后就切换主菜单详情页和侧面菜单的选项颜色
	 */
	@Override
	public void initData() {
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentPos = position;
				mAdapter.notifyDataSetChanged();

				setCurrentMenuDetailPager(position);

				toggleSlidingMenu();// 隐藏
			}
		});
	}

	/**
	 * 切换SlidingMenu的状态
	 * 只要点击了侧边栏任意一项就收起侧边栏
	 * 切换状态, 显示时隐藏, 隐藏时显示
	 */
	protected void toggleSlidingMenu() {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();
	}

	/**
	 * 设置当前菜单在主页的详情页
	 * 
	 * @param position  侧滑菜单被点击的Item条目
	 */
	protected void setCurrentMenuDetailPager(int position) {
		MainActivity mainUi = (MainActivity) mActivity;
		ContentFragment fragment = mainUi.getContentFragment();// 获取主页面fragment
		NewsCenterPager pager = fragment.getNewsCenterPager();// 获取新闻中心页面
		pager.setCurrentMenuDetailPager(position);// 设置当前菜单详情页
	}

	/**
	 *  为了让NewsCenterPager把获取的网络数据传到菜单的fragment(即本类leftMenuFragment)
	 * @param data
	 */
	public void setMenuData(NewsData data) {
		// System.out.println("侧边栏拿到数据啦:" + data);
		/**
		 * data.data已经是一个List所以赋值给mMenuList之后mMenuList没有add操作
		 */
		mMenuList = data.data;
		mAdapter = new MenuAdapter();
		lvList.setAdapter(mAdapter);
	}

	/**
	 * 侧边栏数据适配器
	 * 
	 * @author Kevin
	 * 
	 */
	class MenuAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return mMenuList.size();
		}

		/**
		 * getItem的用法
		 */
		@Override
		public NewsMenuData getItem(int position) {
			return mMenuList.get(position);
		}

//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return mMenuList.get(position);
//		}
		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity, R.layout.list_menu_item, null);
			TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
			/**
			 * 用法----对getitem方法重写的前提下
			 */
			NewsMenuData newsMenuData = getItem(position);
//			NewsMenuData newsMenuData = mMenuList.getItem(position);
			tvTitle.setText(newsMenuData.title);

			if (mCurrentPos == position) {// 判断当前绘制的view是否被选中
				// 显示红色
				tvTitle.setEnabled(true);
			} else {
				// 显示白色
				tvTitle.setEnabled(false);
			}
			return view;
		}
	}
}
