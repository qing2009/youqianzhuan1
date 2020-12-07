package com.shanqb.wallet.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.shanqb.wallet.R;
import com.shanqb.wallet.tabview.HomeActivity;
import com.shanqb.wallet.utils.SharedPreConstants;
import com.shanqb.wallet.utils.SharedPreferencesUtil;
import com.shanqb.wallet.view.PointView;

public class LeadActivity extends BaseActivity
{
	/** 指示当前滑动位置的组件 */
	private PointView pointView;
	/** 在界面中显示的四个滑动页View */
	private ArrayList<View> views;
	/** 滑动组件 */
	private ViewPager viewPager;
	/** 动态界面生成组件 */
	private LayoutInflater inflater;
	/** callID, 时间流逝 */
	public static final int CALL_TIME_GO = FIRST_VAL++;

	/** 是否从关于界面启动该界面 */
	private boolean isFromAbout = false;

	/** 界面是否被销毁标志 */
	private boolean isDestory = false;
	/** 间隔一段时间通知界面更新 */
	Runnable runnable = new Runnable() {
		@Override
		public void run() {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_lead);
		// 默认从非关于界面启动
		isFromAbout = getIntent().getBooleanExtra("isFromAbout", false);
		inflater = LayoutInflater.from(this);
		findViews();
		addAction();
	}

	public void setAdapterForViewPager() {
		viewPager.setAdapter(new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views != null ? views.size() : 0;
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = views.get(position);
				container.addView(view);

				return view;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(views.get(position));
			}
		});
	}

	/** 初始化在界面中显示的四个滑动页 */
	public void initViewPagerViews() {
		views = new ArrayList<View>();
		for (int i = 0; i < 4; i++) {
			View view = inflater.inflate(R.layout.item_lead, null);
			View viewCenter = view.findViewById(R.id.viewCenter);
			View buttonNextStep = view.findViewById(R.id.buttonNextStep);
			switch (i) {
			case 0:
				view.setBackgroundColor(getResources().getColor(R.color.lead_1));
				viewCenter.setBackgroundResource(R.drawable.lead_1);
				buttonNextStep.setVisibility(View.INVISIBLE);
				break;
			case 1:

				view.setBackgroundColor(getResources().getColor(R.color.lead_2));
				viewCenter.setBackgroundResource(R.drawable.lead_2);
				buttonNextStep.setVisibility(View.INVISIBLE);

				break;
			case 2:

				view.setBackgroundColor(getResources().getColor(R.color.lead_3));
				viewCenter.setBackgroundResource(R.drawable.lead_3);
				buttonNextStep.setVisibility(View.INVISIBLE);

				break;
			case 3:

				view.setBackgroundColor(getResources().getColor(R.color.lead_4));
				viewCenter.setBackgroundResource(R.drawable.lead_4);
				buttonNextStep.setVisibility(View.VISIBLE);
				buttonNextStep.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
//						startActivity(new Intent(LeadActivity.this, WebActivity.class).putExtra(
//								"url", Global.BASE_URL));
//						finish();
//						int userid = SharedPreferencesUtil.getIntValue(LeadActivity.this, SharedPreConstants.userid,-1);
//						if (userid>0){
//							startActivity(new Intent(LeadActivity.this, HomeActivity.class));
//						}else {
//							startActivity(new Intent(LeadActivity.this, LoginActivity.class));
//						}
						startActivity(new Intent(LeadActivity.this, LoginActivity.class));

						finish();
					}
				});

				break;
			}
			views.add(view);
		}

	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void call(int id, Object... args) {}

	@Override
	public void findViews() {
		pointView = (PointView) findViewById(R.id.pointView);
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		initViewPagerViews();
		setAdapterForViewPager();
		pointView.setCount(views.size());
	}

	@Override
	public void addAction() {
		addBackAction();
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// 更改滑动进度显示
				pointView.setIndex(arg0);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

}
