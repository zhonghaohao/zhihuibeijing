package com.heima.capter.base.impl;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.heima.capter.activity.MainActivity;
import com.heima.capter.base.BasePager;
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

//	private ArrayList<BaseMenuDetailPager> mPagers;// 4���˵�����ҳ�ļ���
//	private NewsData mNewsData;

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

		/**
		 * ��FrameLayout�ж�̬��Ӳ���
		 */
//		getDataFromServer();
	}

	/**
	 * �ӷ�������ȡ����
	 */
//	private void getDataFromServer() {
//		HttpUtils utils = new HttpUtils();
//
//		// ʹ��xutils��������
//		utils.send(HttpMethod.GET, GlobalContants.CATEGORIES_URL,
//				new RequestCallBack<String>() {
//
//					// ���ʳɹ�
//					@Override
//					public void onSuccess(ResponseInfo responseInfo) {
//						String result = (String) responseInfo.result;
//						System.out.println("���ؽ��:" + result);
//
//						parseData(result);
//					}
//
//					// ����ʧ��
//					@Override
//					public void onFailure(HttpException error, String msg) {
//						Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
//								.show();
//						error.printStackTrace();
//					}
//
//				});
//	}
//
//	/**
//	 * ������������
//	 * 
//	 * @param result
//	 */
//	protected void parseData(String result) {
//		Gson gson = new Gson();
//		mNewsData = gson.fromJson(result, NewsData.class);
//		System.out.println("�������:" + mNewsData);
//
//		// ˢ�²����������
//		MainActivity mainUi = (MainActivity) mActivity;
//		LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
//		leftMenuFragment.setMenuData(mNewsData);
//
//		// ׼��4���˵�����ҳ
//		mPagers = new ArrayList<BaseMenuDetailPager>();
//		mPagers.add(new NewsMenuDetailPager(mActivity,
//				mNewsData.data.get(0).children));
//		mPagers.add(new TopicMenuDetailPager(mActivity));
//		mPagers.add(new PhotoMenuDetailPager(mActivity));
//		mPagers.add(new InteractMenuDetailPager(mActivity));
//
//		setCurrentMenuDetailPager(0);// ���ò˵�����ҳ-����ΪĬ�ϵ�ǰҳ
//	}
//
//	/**
//	 * ���õ�ǰ�˵�����ҳ
//	 */
//	public void setCurrentMenuDetailPager(int position) {
//		BaseMenuDetailPager pager = mPagers.get(position);// ��ȡ��ǰҪ��ʾ�Ĳ˵�����ҳ
//		flContent.removeAllViews();// ���֮ǰ�Ĳ���
//		flContent.addView(pager.mRootView);// ���˵�����ҳ�Ĳ������ø�֡����
//
//		// ���õ�ǰҳ�ı���
//		NewsMenuData menuData = mNewsData.data.get(position);
//		tvTitle.setText(menuData.title);
//
//		pager.initData();// ��ʼ����ǰҳ�������
//	}

}

