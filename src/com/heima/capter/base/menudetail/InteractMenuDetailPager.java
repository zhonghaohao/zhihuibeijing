package com.heima.capter.base.menudetail;

import com.heima.capter.base.BaseMenuDetailPager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * ²Ëµ¥ÏêÇéÒ³-»¥¶¯
 * 
 * @author Kevin
 * 
 */
public class InteractMenuDetailPager extends BaseMenuDetailPager {

	public InteractMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initViews() {
		TextView text = new TextView(mActivity);
		text.setText("²Ëµ¥ÏêÇéÒ³-»¥¶¯");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);

		return text;
	}

}
