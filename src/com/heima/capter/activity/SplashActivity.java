package com.heima.capter.activity;

import com.heima.capter.R;
import com.heima.capter.utils.SPUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {
	private RelativeLayout rl_root;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		rl_root = (RelativeLayout) findViewById(R.id.rl_root);
		playAnimation();
	}
	/**
	 * ִ�ж�������ת�����ţ�����
	 */
	public void playAnimation(){
		AnimationSet set = new AnimationSet(false);
		//���䶯��
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(500);
		alphaAnimation.setFillAfter(true);
		
		//���Ŷ���
		ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(500);
		scaleAnimation.setFillAfter(true);
		
		//��ת����
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(500);
		rotateAnimation.setFillAfter(true);
		
		set.addAnimation(rotateAnimation);
		set.addAnimation(alphaAnimation);
		set.addAnimation(scaleAnimation);
		
		//���������ü������Ա����ʲôʱ����������ҳ��
		set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			//��������ʱ����ת����һ��ҳ��
			@Override
			public void onAnimationEnd(Animation animation) {
				jumpNextPage();
			}
		});
		rl_root.startAnimation(set);
		
	}
	/**
	 * ͨ���ж�sp�е�ֵ��������splash���뵽����ҳ����ֱ�ӽ�����ҳ��
	 */
	private void jumpNextPage(){
		Boolean  isShowed = SPUtils.getBoolean(this, "is_user_guide_showed", false);
		if(!isShowed){
			startActivity(new Intent(SplashActivity.this, GuideActivity.class));
		}else{
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}
		finish();
	}
}
