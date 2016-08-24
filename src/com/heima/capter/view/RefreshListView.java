package com.heima.capter.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.heima.capter.R;
import com.heima.capter.base.impl.NewsCenterPager;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class RefreshListView extends ListView implements OnScrollListener, android.widget.AdapterView.OnItemClickListener{

	/**
	 * 按下手指的Y位置
	 */
	private int startY;
	/**
	 * headerView布局的高度
	 */
	private int mHeaderViewHeight;
	private View mHeaderView;
	private int mFooterViewHeight;
	private View mFooterView;
	/**
	 * 下拉刷新
	 */
	private static final int STATE_PULL_REFRESH = 0;// 下拉刷新
	/**
	 * 松开刷新
	 */
	private static final int STATE_RELEASE_REFRESH = 1;// 松开刷新
	/**
	 * 正在刷新
	 */
	private static final int STATE_REFRESHING = 2;// 正在刷新
	/**
	 * 当前状态
	 */
	private int mCurrrentState = STATE_PULL_REFRESH;// 当前状态

	private boolean isLoadingMore;

	private TextView tvTitle;
	private TextView tvTime;
	private ImageView ivArrow;
	private ProgressBar pbProgress;
	private RotateAnimation animUp;
	private RotateAnimation animDown;
	
	private OnRefreshListener mlistener;
	private OnItemClickListener mItemClickListener;

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeaderView();
		initArrowAnim();
		initFooterView();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initArrowAnim();
		initFooterView();
	}

	public RefreshListView(Context context) {
		super(context);
		initHeaderView();
		initArrowAnim();
		initFooterView();
	}

	private void initHeaderView() {
		mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
		this.addHeaderView(mHeaderView);
		tvTitle = (TextView) mHeaderView.findViewById(R.id.tv_title);
		tvTime = (TextView) mHeaderView.findViewById(R.id.tv_time);
		ivArrow = (ImageView) mHeaderView.findViewById(R.id.iv_arr);
		pbProgress = (ProgressBar) mHeaderView.findViewById(R.id.pb_progress);
		mHeaderView.measure(0, 0);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
		tvTime.setText("最近刷新：" + getCurrentTime());
	}

	/*
	 * 初始化脚布局
	 */
	private void initFooterView() {
		mFooterView = View.inflate(getContext(),
				R.layout.refresh_listview_footer, null);
		this.addFooterView(mFooterView);

		mFooterView.measure(0, 0);
		mFooterViewHeight = mFooterView.getMeasuredHeight();

		mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);// 隐藏

		this.setOnScrollListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// startY = (int) ev.getRawY();
			startY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			/**
			 * 可能按下的时候没有记录到数据
			 */
			if (startY == -1) {
				// startY = (int) ev.getRawY();
				startY = (int) ev.getY();
			}
			if (mCurrrentState == STATE_REFRESHING) {
				break;// 正在刷新时不可以滑动
			}
			// int endY = (int) ev.getRawY();
			int endY = (int) ev.getY();
			int dy = endY - startY;
			if (dy > 0 && getFirstVisiblePosition() == 0) {
				int padding = dy - mHeaderViewHeight;
				mHeaderView.setPadding(0, padding, 0, 0);
				if (padding > 0 && mCurrrentState == STATE_PULL_REFRESH) {
					// 只有当可见的是第一个Item,且当前不是松开刷新的状态
					mCurrrentState = STATE_RELEASE_REFRESH;
					refreshState();
				} else if (padding < 0
						&& mCurrrentState == STATE_RELEASE_REFRESH) {
					// 下拉刷新
					mCurrrentState = STATE_PULL_REFRESH;
					refreshState();
				}

				// System.out.println("startY: " + startY + " endy: " + endY +
				// " dy: " +dy + " padding: " + padding);

				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			startY = -1;
			if (mCurrrentState == STATE_RELEASE_REFRESH) {
				// 处于松开刷新时抬起，要变为正在刷新状态
				mCurrrentState = STATE_REFRESHING;
				mHeaderView.setPadding(0, 0, 0, 0);
				refreshState();
			} else if (mCurrrentState == STATE_PULL_REFRESH) {
				// 正处于下拉刷新状态时抬起
				mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);
			}
			break;
		default:
			break;

		}
		return super.onTouchEvent(ev);
	}

	private void refreshState() {
		switch (mCurrrentState) {
		case STATE_PULL_REFRESH:
			tvTitle.setText("下拉刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			ivArrow.startAnimation(animDown);
			break;
		case STATE_RELEASE_REFRESH:
			tvTitle.setText("松开刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			ivArrow.startAnimation(animUp);
			break;
		case STATE_REFRESHING:
			tvTitle.setText("正在刷新");
			ivArrow.clearAnimation();// 必须先清除动画,才能隐藏
			ivArrow.setVisibility(View.INVISIBLE);
			pbProgress.setVisibility(View.VISIBLE);

			if (mlistener != null) {
				mlistener.onRefresh();
			}
			break;
		}
	}

	/**
	 * 初始化箭头动画
	 */
	private void initArrowAnim() {
		// 箭头向上动画
		animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animUp.setDuration(200);
		animUp.setFillAfter(true);

		// 箭头向下动画
		animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animDown.setDuration(200);
		animDown.setFillAfter(true);
	}

	/*
	 * 收起下拉刷新的控件
	 */
	public void onRefreshComplete(boolean success) {
		if(isLoadingMore){
			mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
			isLoadingMore = false;
		}else{
			mCurrrentState = STATE_PULL_REFRESH;
			tvTitle.setText("下拉刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			mHeaderView.setPadding(0, -mHeaderViewHeight, 0, 0);// 隐藏
		}
		if (success) {
			tvTime.setText("最近刷新：" + getCurrentTime());
		}
	}

	/**
	 * 获取当前时间
	 */
	public String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	

	public void setOnRefreshListener(OnRefreshListener listener) {
		mlistener = listener;
	}

	public interface OnRefreshListener {
		public void onRefresh();

		public void onLoadMore();
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState == SCROLL_STATE_IDLE
				|| scrollState == SCROLL_STATE_FLING) {

			if (getLastVisiblePosition() == getCount() - 1 && !isLoadingMore) {// 滑动到最后
//				System.out.println("到底了.....");
				mFooterView.setPadding(0, 0, 0, 0);// 显示
				setSelection(getCount() - 1);// 改变listview显示位置

				isLoadingMore = true;

				if (mlistener != null) {
					mlistener.onLoadMore();
				}
			}
		}
	}

	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}
	
	/**
	 * 为了解决自定义listView在设置itemClick事件时的position因为有两个header的影响，
	 * 通过让自定义的listView实现OnItemClickListener类，重写方法，改变position的值，
	 * 记住要将自己的this传入父类方法
	 */
	@Override
	public void setOnItemClickListener(
			android.widget.AdapterView.OnItemClickListener listener) {
//		super.setOnItemClickListener(listener);
		super.setOnItemClickListener(this);
		mItemClickListener = listener;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if(mItemClickListener != null){
			mItemClickListener.onItemClick(parent, view, position - getHeaderViewsCount(), id);
		}
	}
}
