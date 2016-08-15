package com.heima.capter.fragment;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.heima.capter.R;
import com.heima.capter.activity.MainActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * �����
 * 
 * @author Kevin
 * 
 */
public class LeftMenuFragment extends BaseFragment {

	@ViewInject(R.id.lv_list)
	private ListView lvList;
//	private ArrayList<NewsMenuData> mMenuList;

	private int mCurrentPos;// ��ǰ������Ĳ˵���
//	private MenuAdapter mAdapter;

	@Override
	public View initViews() {
		View view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	public void initData() {
		lvList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentPos = position;
//				mAdapter.notifyDataSetChanged();

				setCurrentMenuDetailPager(position);

				toggleSlidingMenu();// ����
			}
		});
	}

	/**
	 * �л�SlidingMenu��״̬
	 * 
	 * @param b
	 */
	protected void toggleSlidingMenu() {
		MainActivity mainUi = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		slidingMenu.toggle();// �л�״̬, ��ʾʱ����, ����ʱ��ʾ
	}

	/**
	 * ���õ�ǰ�˵�����ҳ
	 * 
	 * @param position
	 */
	protected void setCurrentMenuDetailPager(int position) {
		MainActivity mainUi = (MainActivity) mActivity;
//		ContentFragment fragment = mainUi.getContentFragment();// ��ȡ��ҳ��fragment
//		NewsCenterPager pager = fragment.getNewsCenterPager();// ��ȡ��������ҳ��
//		pager.setCurrentMenuDetailPager(position);// ���õ�ǰ�˵�����ҳ
	}

	// ������������
//	public void setMenuData(NewsData data) {
//		// System.out.println("������õ�������:" + data);
//		mMenuList = data.data;
//		mAdapter = new MenuAdapter();
//		lvList.setAdapter(mAdapter);
//	}

	/**
	 * ���������������
	 * 
	 * @author Kevin
	 * 
	 */
//	class MenuAdapter extends BaseAdapter {
//
//		@Override
//		public int getCount() {
//			return mMenuList.size();
//		}
//
//		@Override
//		public NewsMenuData getItem(int position) {
//			return mMenuList.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			View view = View.inflate(mActivity, R.layout.list_menu_item, null);
//			TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
//			NewsMenuData newsMenuData = getItem(position);
//			tvTitle.setText(newsMenuData.title);
//
//			if (mCurrentPos == position) {// �жϵ�ǰ���Ƶ�view�Ƿ�ѡ��
//				// ��ʾ��ɫ
//				tvTitle.setEnabled(true);
//			} else {
//				// ��ʾ��ɫ
//				tvTitle.setEnabled(false);
//			}
//
//			return view;
//		}
//
//	}

}
