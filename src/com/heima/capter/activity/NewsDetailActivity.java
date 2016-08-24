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
	 * ��¼��ǰѡ�е�item, ���ȷ��ǰ
	 */
	private int mCurrentChooseItem;
	/**
	 * ��¼��ǰѡ�е�item, ���ȷ����
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
		settings.setJavaScriptEnabled(true);// ��ʾ֧��js������չ��ȫ���Ĺ���
		settings.setBuiltInZoomControls(true);// ��ʾ�Ŵ���С��ť
		settings.setUseWideViewPort(true);// ֧��˫������
		//�������ֵĴ�С
		mCurrentItem = SPUtils.getInt(NewsDetailActivity.this, "text_size", 2);
		mCurrentChooseItem = mCurrentItem;
		selectTextSize();
		// mWebView.goBack();//������һҳ
		mWebView.setWebViewClient(new WebViewClient() {
			/**
			 * ��ҳ��ʼ����
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				System.out.println("��ҳ��ʼ����");
				pbProgress.setVisibility(View.VISIBLE);
			}

			/**
			 * ��ҳ���ؽ���
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				System.out.println("��ҳ���ؽ���");

				pbProgress.setVisibility(View.INVISIBLE);
			}

			/**
			 * ������ת�����Ӷ����ڴ˷����лص�
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// tel:110
				System.out.println("��תurl:" + url);
				/**
				 * ����Ҫ��ת����ҳ�����ڱ�Ӧ������ʾ�������������������
				 */
				view.loadUrl(url);
				return true;
				// return super.shouldOverrideUrlLoading(view, url);
			}
		});
		mWebView.setWebChromeClient(new WebChromeClient() {
			/**
			 * �յ���ҳ���ؽ���
			 */
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// System.out.println("newProgress: " + newProgress);
				super.onProgressChanged(view, newProgress);
			}

			/**
			 * �յ���ҳ����
			 */
			@Override
			public void onReceivedTitle(WebView view, String title) {
				// System.out.println("title: " + title);
				super.onReceivedTitle(view, title);
			}

			/**
			 * �յ���ҳlogo
			 */
			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {
				super.onReceivedIcon(view, icon);
			}
		});

		/**
		 * ��WebView������ҳ
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
		String[] items = new String[] { "���������", "�������", "��������", "С������",
				"��С������" };

		builder.setSingleChoiceItems(items, mCurrentItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mCurrentChooseItem = which;
						System.out.println("�����mCurrentChooseItem: "
								+ mCurrentChooseItem);
					}
				});
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				selectTextSize();
				mCurrentItem = mCurrentChooseItem;
				SPUtils.setInt(NewsDetailActivity.this, "text_size",
						mCurrentItem);
				System.out.println("ȷ�Ϻ�mCurrentItem: " + mCurrentItem);
			}
		});
		builder.setNegativeButton("ȡ��", null);

		builder.show();
	}

	/**
	 * ��������Ĵ�С
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
	 * ��������
	 */
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// �ر�sso��Ȩ
		oks.disableSSOWhenAuthorize();
		
		//���ö������ƽ̨չʾ������
		oks.setTheme(OnekeyShareTheme.SKYBLUE);
		// ����ʱNotification��ͼ������� 2.5.9�Ժ�İ汾�����ô˷���
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		oks.setTitle(getString(R.string.share));
		// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		oks.setTitleUrl("http://sharesdk.cn");
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText("���Ƿ����ı�");
		// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		// oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		// url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		oks.setUrl("http://sharesdk.cn");
		// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		oks.setComment("���ǲ��������ı�");
		// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		oks.setSite(getString(R.string.app_name));
		// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		oks.setSiteUrl("http://sharesdk.cn");

		// ��������GUI
		oks.show(this);
	}

}
