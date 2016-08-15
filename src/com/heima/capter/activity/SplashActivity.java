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
	 * 执行动画：旋转，缩放，渐变
	 */
	public void playAnimation(){
		AnimationSet set = new AnimationSet(false);
		//渐变动画
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(500);
		alphaAnimation.setFillAfter(true);
		
		//缩放动画
		ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(500);
		scaleAnimation.setFillAfter(true);
		
		//旋转动画
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(500);
		rotateAnimation.setFillAfter(true);
		
		set.addAnimation(rotateAnimation);
		set.addAnimation(alphaAnimation);
		set.addAnimation(scaleAnimation);
		
		//给动画设置监听，以便决定什么时候启动引导页面
		set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			//动画结束时才跳转到下一个页面
			@Override
			public void onAnimationEnd(Animation animation) {
				jumpNextPage();
			}
		});
		rl_root.startAnimation(set);
		
	}
	/**
	 * 通过判断sp中的值，决定从splash进入到引导页还是直接进入主页面
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
