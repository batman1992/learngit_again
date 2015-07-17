package com.example.scrollhuadong;

import java.util.ArrayList;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author luohaiying
 *
 * @date 2015-4-7
 */
public class MainActivity extends FragmentActivity {
	/** 自定义HorizontalScrollView */
	private ColumnHorizontalScrollView mColumnHorizontalScrollView;
	LinearLayout mRadioGroup_content;
	private ViewPager mViewPager;
	/** 用户选择的新闻分类列表 */
	private ArrayList<ChannelItem> userChannelList = new ArrayList<ChannelItem>();
	/** 当前选中的栏目 */
	private int columnSelectIndex = 0;
	/** 屏幕宽度 */
	private int mScreenWidth = 0;
	/** Item宽度 */
	private int mItemWidth = 0;
	View view;
	TextView iv,tv,line;
	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	private LayoutInflater mInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mScreenWidth = BaseTools.getWindowsWidth(this);
		mItemWidth = mScreenWidth/4;// 一个Item宽度为屏幕的1/4
		initView();
	}

	/** 初始化layout控件 */
	private void initView() {
		mInflater = LayoutInflater.from(this);
		mColumnHorizontalScrollView = (ColumnHorizontalScrollView) findViewById(R.id.mColumnHorizontalScrollView);
		mRadioGroup_content = (LinearLayout) findViewById(R.id.mRadioGroup_content);
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		setChangelView();
	}

	/**
	 * 当栏目项发生变化时候调用
	 * */
	private void setChangelView() {
		initColumnData();
		initTabColumn();
		initFragment();
	}

	/** 获取Column栏目 数据 */
	private void initColumnData() {
		userChannelList = new ArrayList<ChannelItem>();
		ChannelItem item = new ChannelItem();
		item.setName("全部订单1");
		item.setId(1);
		item.setOrderId(1);
		item.setSelected(1);
		userChannelList.add(item);

		item = new ChannelItem();
		item.setName("全部订单2");
		item.setId(2);
		item.setOrderId(2);
		item.setSelected(2);
		userChannelList.add(item);

		item = new ChannelItem();
		item.setName("全部订单3");
		item.setId(3);
		item.setOrderId(3);
		item.setSelected(3);
		userChannelList.add(item);

		item = new ChannelItem();
		item.setName("全部订单4");
		item.setId(4);
		item.setOrderId(4);
		item.setSelected(4);
		userChannelList.add(item);

		item = new ChannelItem();
		item.setName("全部订单5");
		item.setId(5);
		item.setOrderId(5);
		item.setSelected(5);
		userChannelList.add(item);

		item = new ChannelItem();
		item.setName("全部订单6");
		item.setId(6);
		item.setOrderId(6);
		item.setSelected(6);
		userChannelList.add(item);

		item = new ChannelItem();
		item.setName("全部订单7");
		item.setId(7);
		item.setOrderId(7);
		item.setSelected(7);
		userChannelList.add(item);
	}

	/**
	 * 初始化Column栏目项
	 * */
	private void initTabColumn() {
		mRadioGroup_content.removeAllViews();
		int count = userChannelList.size();
		mColumnHorizontalScrollView.setParam(this, mScreenWidth,
				mRadioGroup_content);
		for (int i = 0; i < count; i++) {
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					mItemWidth, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 5;
			params.rightMargin = 5;
			view=mInflater.inflate(R.layout.itme, null);
			iv=(TextView) view.findViewById(R.id.iv);
			tv=(TextView) view.findViewById(R.id.tv);
			line=(TextView) view.findViewById(R.id.line);
			iv.setBackgroundResource(R.drawable.iv_1);
			line.setBackgroundResource(R.drawable.line);
			tv.setId(i);
			tv.setText(userChannelList.get(i).getName());
			tv.setTextColor(getResources().getColorStateList(R.color.top_category_scroll_text_color_day));
			if (columnSelectIndex == i) {
				iv.setSelected(true);
				tv.setSelected(true);
				line.setSelected(true);
			}
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
						View localView = mRadioGroup_content.getChildAt(i);
						if (localView != v)
							localView.setSelected(false);
						else {
							localView.setSelected(true);
							mViewPager.setCurrentItem(i);
						}
					}
				}
			});
			mRadioGroup_content.addView(view, i,params);
		}
	}

	/**
	 * 初始化Fragment
	 * */
	private void initFragment() {
		fragments.clear();// 清空
		int count = userChannelList.size();
		for (int i = 0; i < count; i++) {
			Bundle data = new Bundle();
			data.putString("text", userChannelList.get(i).getName());
			data.putInt("id", userChannelList.get(i).getId());
			CountingFragment newfragment = new CountingFragment();
			newfragment.setArguments(data);
			fragments.add(newfragment);
		}
		NewsFragmentPagerAdapter mAdapetr = new NewsFragmentPagerAdapter(
				getSupportFragmentManager(), fragments);
		// mViewPager.setOffscreenPageLimit(0);
		mViewPager.setAdapter(mAdapetr);
		mViewPager.setOnPageChangeListener(pageListener);
	}

	/**
	 * ViewPager切换监听方法
	 * */
	public OnPageChangeListener pageListener = new OnPageChangeListener() {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int position) {
			Log.e("position==", ""+position);
			mViewPager.setCurrentItem(position);
			selectTab(position);
			initTabColumn();
		}
	};

	/**
	 * 选择的Column里面的Tab
	 * */
	private void selectTab(int tab_postion) {
		columnSelectIndex = tab_postion;
		for (int i = 0; i < mRadioGroup_content.getChildCount(); i++) {
			View checkView = mRadioGroup_content.getChildAt(tab_postion);
			int k = checkView.getMeasuredWidth();
			int l = checkView.getLeft();
			int i2 = l + k / 2 - mScreenWidth / 2;
			// rg_nav_content.getParent()).smoothScrollTo(i2, 0);
			mColumnHorizontalScrollView.smoothScrollTo(i2, 0);
			// mColumnHorizontalScrollView.smoothScrollTo((position - 2) *
			// mItemWidth , 0);
		}
		// 判断是否选中
		for (int j = 0; j < mRadioGroup_content.getChildCount(); j++) {
			View checkView = mRadioGroup_content.getChildAt(j);
			boolean ischeck;
			if (j == tab_postion) {
				ischeck = true;
			} else {
				ischeck = false;
			}
			checkView.setSelected(ischeck);
		}
	}

}
