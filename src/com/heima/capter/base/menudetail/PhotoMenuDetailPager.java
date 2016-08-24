package com.heima.capter.base.menudetail;

import com.heima.capter.base.BaseMenuDetailPager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * �˵�����ҳ-��ͼ
 * 
 * @author Kevin
 * 
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPager {

	public PhotoMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initViews() {
		TextView text = new TextView(mActivity);
		text.setText("�˵�����ҳ-��ͼ");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);

		return text;
	}

}
