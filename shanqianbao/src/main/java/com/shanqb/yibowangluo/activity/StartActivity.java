package com.shanqb.yibowangluo.activity;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.shanqb.yibowangluo.R;
import com.shanqb.yibowangluo.bean.VerResp;
import com.shanqb.yibowangluo.utils.Global;
import com.shanqb.yibowangluo.utils.LogUtils;
import com.umeng.analytics.MobclickAgent;

public class StartActivity extends BaseActivity
{

	static int CALL_START_OVER = FIRST_VAL++;
	static int CALL_CHECK_VER = FIRST_VAL++;

	/** 页面停留时间 */
	static final long PAUSE_TIME = 2000L;
	/** 页面启动时间 */
	long startTime;

	public void findViews() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_start);

		MobclickAgent.setDebugMode(false);
		MobclickAgent.openActivityDurationTrack(false);

		MobclickAgent.updateOnlineConfig(this);

		startTime = System.currentTimeMillis();

		new Thread(runnable).start();
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// try {
			// Thread.sleep(PAUSE_TIME);
			// } catch (InterruptedException e) {
			// e.printStackTrace();
			// }
			checkVer();
			// runCallFunctionInHandler( CALL_START_OVER );
		}
	};

	public void checkVer() {
//		String resp = new NetworkUtils(StartActivity.this).sendAndWaitResponse((String) null,
//				Global.UPDATE_URL, "UTF-8", true);
//
//		LogUtils.debug("resp=" + resp);
		VerResp ver = null;
		try {
			//ver = JSON.parseObject(resp, VerResp.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		runCallFunctionInHandler(CALL_CHECK_VER, ver);

		LogUtils.debug(ver);
	}

	public void goNext() {

		final long now = System.currentTimeMillis();

		if (now - startTime < PAUSE_TIME) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(PAUSE_TIME - (now - startTime));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					runCallFunctionInHandler(CALL_START_OVER);
				}
			}).start();

			return;
		}

//		int versionCodeOld = DataKeeper.getVersionCode(this);
//		int versionCode = Global.getVersionCode(this);
//		if (versionCode > versionCodeOld) {
//			startActivity(new Intent(this, LeadActivity.class));
//			DataKeeper.saveVersionCode(this, versionCode);
//			finish();
//		} else {

//			int userid = SharedPreferencesUtil.getIntValue(this, SharedPreConstants.userid,-1);
//			if (userid>0){
//				startActivity(new Intent(this, WebActivity.class).putExtra("url", Global.BASE_URL));
//				startActivity(new Intent(this, HomeActivity.class));
//			}else {
//				startActivity(new Intent(this, LoginActivity.class));
//			}
			startActivity(new Intent(this, LoginActivity.class));

			finish();
//		}
	}

	public void onClick(View v) {

	};

	public void call(int callID, Object... args) {
		if (CALL_START_OVER == callID) {
			goNext();
		}
		if (CALL_CHECK_VER == callID) {

			if (args[0] != null) {
				VerResp ver = (VerResp) args[0];
				if ("200".equals(ver.getStatus())) {

					int code = Global.getVersionCode(this);
					if (code >= ver.getVersion().getLatest().getVersionCode()) {
						// 不需要更新
						runCallFunctionInHandler(CALL_START_OVER);
						return;
					}

					Builder builder = new Builder(this);
					final String url = ver.getVersion().getLatest().getDownloadURL();
					builder.setTitle(getString(R.string.app_name));

					boolean isMustUpdate = false;
					if (code < ver.getVersion().getMinimal().getVersionCode()) {
						// 强制更新
						builder.setMessage(ver.getVersion().getMinimal().getMessage());
						isMustUpdate = true;
					}
					else {
						// 可选更新
						builder.setMessage(ver.getVersion().getLatest().getVersionDesc());

					}

					if (isMustUpdate) {
						builder.setPositiveButton("更新", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
//								startDownLoad(url);
								finish();
							}
						});

						builder.setNegativeButton("取消", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								finish();
							}
						});
					}
					else {
						builder.setPositiveButton("更新", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
//								startDownLoad(url);
								runCallFunctionInHandler(CALL_START_OVER);

							}
						});

						builder.setNegativeButton("取消", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								runCallFunctionInHandler(CALL_START_OVER);
							}
						});
					}

					builder.setCancelable(false);
					builder.create().show();
				}
			}

			else {
				runCallFunctionInHandler(CALL_START_OVER);
			}
		}
	};


	@Override
	public void addAction() {
		addBackAction();
	}

}
