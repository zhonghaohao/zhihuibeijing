package com.heima.capter.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

import com.heima.capter.R;
import com.heima.capter.utils.SPUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class NewsDetailActivity extends Activity implements OnClickListener {
	private WebView mWebView;
	private ImageButton btnBack;
	private ImageButton btnSize;
	private ImageButton btnShare;
	private ProgressBar pbProgress;
	/**
	 * 记录当前选中的item, 点击确定前
	 */
	private int mCurrentChooseItem;
	/**
	 * 记录当前选中的item, 点击确定后
	 */
	private int mCurrentItem;
	private WebSettings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_detail);

		mWebView = (WebView) findViewById(R.id.wv_web);
		btnBack = (ImageButton) findViewById(R.id.btn_back);
		btnSize = (ImageButton) findViewById(R.id.btn_size);
		btnShare = (ImageButton) findViewById(R.id.btn_share);

		btnBack.setOnClickListener(this);
		btnSize.setOnClickListener(this);
		btnShare.setOnClickListener(this);

		pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
		String url = getIntent().getStringExtra("url");
		settings = mWebView.getSettings();
		settings.setJavaScriptEnabled(true);// 表示支持js，可以展开全部的功能
		settings.setBuiltInZoomControls(true);// 显示放大缩小按钮
		settings.setUseWideViewPort(true);// 支持双击缩放
		//设置文字的大小
		mCurrentItem = SPUtils.getInt(NewsDetailActivity.this, "text_size", 2);
		mCurrentChooseItem = mCurrentItem;
		selectTextSize();
		// mWebView.goBack();//返回上一页
		mWebView.setWebViewClient(new WebViewClient() {
			/**
			 * 网页开始加载
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				System.out.println("网页开始加载");
				pbProgress.setVisibility(View.VISIBLE);
			}

			/**
			 * 网页加载结束
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				System.out.println("网页加载结束");

				pbProgress.setVisibility(View.INVISIBLE);
			}

			/**
			 * 所有跳转的链接都会在此方法中回调
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// tel:110
				System.out.println("跳转url:" + url);
				/**
				 * 将将要跳转的网页内容在本应用中显示，而不是跳到浏览器中
				 */
				view.loadUrl(url);
				return true;
				// return super.shouldOverrideUrlLoading(view, url);
			}
		});
		mWebView.setWebChromeClient(new WebChromeClient() {
			/**
			 * 收到网页加载进度
			 */
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// System.out.println("newProgress: " + newProgress);
				super.onProgressChanged(view, newProgress);
			}

			/**
			 * 收到网页标题
			 */
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// System.out.println("title: " + title);
				super.onReceivedTitle(view, title);
			}

			/**
			 * 收到网页logo
			 */
			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {
				super.onReceivedIcon(view, icon);
			}
		});

		/**
		 * 给WebView加载网页
		 */
		mWebView.loadUrl(url);
		// mWebView.loadUrl("http://www.itheima.cn");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:

			break;
		case R.id.btn_size:
			showChooseDialog();
			break;
		case R.id.btn_share:
			showShare();
			break;

		default:
			break;
		}
	}

	private void showChooseDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体",
				"超小号字体" };

		builder.setSingleChoiceItems(items, mCurrentItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mCurrentChooseItem = which;
						System.out.println("单点击mCurrentChooseItem: "
								+ mCurrentChooseItem);
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				selectTextSize();
				mCurrentItem = mCurrentChooseItem;
				SPUtils.setInt(NewsDetailActivity.this, "text_size",
						mCurrentItem);
				System.out.println("确认后mCurrentItem: " + mCurrentItem);
			}
		});
		builder.setNegativeButton("取消", null);

		builder.show();
	}

	/**
	 * 设置字体的大小
	 */
	private void selectTextSize() {
		switch (mCurrentChooseItem) {
		case 0:
			settings.setTextZoom(200);
			break;
		case 1:
			settings.setTextZoom(150);
			break;
		case 2:
			settings.setTextZoom(100);
			break;
		case 3:
			settings.setTextZoom(75);
			break;
		case 4:
			settings.setTextZoom(50);
			break;

		default:
			break;
		}
	}
	/**
	 * 分享内容
	 */
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();
		
		//设置多个分享平台展示的主题
		oks.setTheme(OnekeyShareTheme.SKYBLUE);
		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		oks.show(this);
	}

}
