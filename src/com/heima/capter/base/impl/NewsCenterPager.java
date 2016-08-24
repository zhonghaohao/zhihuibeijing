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
 * ��������
 * 
 * @author Kevin
 * 
 */
public class NewsCenterPager extends BasePager {
	/**
	 * �ĸ��˵��������ļ���
	 */
	private List<BaseMenuDetailPager> mPagers;
	/**
	 * ��������----Xml�ĸ��ڵ�
	 */
	private NewsData  mNewsData;

	public NewsCenterPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
		/**
		 * ���ڲ��Կ�ViewPager�Ƿ�Ԥ����
		 */
		System.out.println("��ʼ��������������....");

		tvTitle.setText("����");
		/**
		 *  ��ʾ������˵���ť
		 */
		btnMenu.setVisibility(View.VISIBLE);
		/**
		 *  �����Ƿ���Դ򿪲����
		 */
		setSlidingMenuEnable(true);
		String cache = CacheUtils.getCache(GlobalContants.CATEGORIES_URL, mActivity);
		if(!TextUtils.isEmpty(cache)){
			parseData(cache);
		}
		/**
		 * �ӷ������õ�������FrameLayout�ж�̬��Ӳ���
		 * ע�ⲻ���Ƿ��л��涼Ҫ������������ݣ���Ϊ�����������и�������
		 * ������Ϊ����ǿ�û�����
		 */
		getDataFromServer();
	}

	/**
	 * �ӷ�������ȡ����
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();

		/**
		 *  ʹ��xutils��������
		 *  �˴��ķ���StringҪ�������󷵻ص��������ã��п����Ǹ��ļ��ȵ�
		 */
		utils.send(HttpMethod.GET, GlobalContants.CATEGORIES_URL,
				new RequestCallBack<String>() {

					// ���ʳɹ�
					@Override
					public void onSuccess(ResponseInfo responseInfo) {
						/**
						 * ��ȡ���������ص�����
						 */
						String result = (String) responseInfo.result;
//						System.out.println("���ؽ��:" + result);
//						System.out.println("�ɹ���������");
						Toast.makeText(mActivity, "�������ݳɹ�", Toast.LENGTH_SHORT)
						.show();

						parseData(result);
						CacheUtils.setCache(GlobalContants.CATEGORIES_URL, result, mActivity);
					}

					// ����ʧ��
					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, "��������ʧ��", Toast.LENGTH_SHORT)
								.show();
//						System.out.println("��������ʧ��");
						error.printStackTrace();
					}

				});
	}

	/**
	 * �����������ݣ������󵽵����ݽ���class����
	 * 
	 * @param result ��json�ļ�
	 */
	protected void parseData(String result) {
		Gson gson = new Gson();
		mNewsData = gson.fromJson(result, NewsData.class);
//		System.out.println("�������:" + mNewsData);

		/**
		 *  ˢ�²����������
		 */
		MainActivity mainUi = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
		/**
		 * ���������leftMenuFragment���У�����������ʾ�����listView����
		 */
		leftMenuFragment.setMenuData(mNewsData);

		// ׼��4���˵�����ҳ
		mPagers = new ArrayList<BaseMenuDetailPager>();
		mPagers.add(new NewsMenuDetailPager(mActivity,
				mNewsData.data.get(0).children));
		mPagers.add(new TopicMenuDetailPager(mActivity));
		mPagers.add(new PhotoMenuDetailPager(mActivity));
		mPagers.add(new InteractMenuDetailPager(mActivity));
		
		/**
		 * �������˵�����ҳ-�໬�˵�������Ϊ���˵���Ĭ�ϵ�ǰҳ
		 */
		setCurrentMenuDetailPager(0);
	}

	/**
	 * ���ݲ�߲˵��ĵ��ѡ���л����˵���Viewpager�в�����Ϣ
	 * @param position  ��߱������Item
	 */
	public void setCurrentMenuDetailPager(int position) {
		BaseMenuDetailPager pager = mPagers.get(position);// ��ȡ��ǰҪ��ʾ�Ĳ˵�����ҳ
		flContent.removeAllViews();// ���֮ǰ�Ĳ���
		/**
		 * ���˵�����ҳ�Ĳ������ø�֡����
		 * pager.mRootView���mRootView������NewsMenuDetailPager���ĸ�ҳ��ʵ��
		 * �������ڱ���ӵ�ʱ��(�Զ��������ʱ��),�����Ѿ�ʵ����initView()����
		 * ����mRootView�����Ѿ���ʼ���õ�
		 */
		flContent.addView(pager.mRootView);

		/**
		 *  ���õ�ǰҳ���Ϸ��ı���(title),���Ǹտ�ʼ��ʱ��д�Ĺ̶�����
		 */
		NewsMenuData menuData = mNewsData.data.get(position);
		tvTitle.setText(menuData.title);
		/**
		 *  ��ʼ����ǰҳ�������(��ͨ�����ø���BaseMenuDetailPager��initData()��ӵ�������
		 *  NewsMenuDetailPager�ȵ�initData()��������NewsMenuDetailPager���������
		 *  ���Բ�ͬ����ʵ��)
		 */
		pager.initData();
	}

}

