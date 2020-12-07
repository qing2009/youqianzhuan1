package com.shanqb.shanqianbao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.shanqb.wallet.R;
import com.shanqb.wallet.tabview.HomeActivity;
import com.shanqb.wallet.utils.MittUtils;

public class MainActivity extends Activity
{

	private TextView textViewTest;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		textViewTest = (TextView) findViewById(R.id.textViewTest);
		
		textViewTest.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				getMSA();
//				startActivity(new Intent(MainActivity.this,
//						WebActivity.class).putExtra("url", "http://192.168.1.5:8084/XWGame/front/index"));
//				startActivity(new Intent(MainActivity.this, Login.class));
				startActivity(new Intent(MainActivity.this, HomeActivity.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void getMSA(){
		new MittUtils().getDeviceIds(this, new MittUtils.AppIdsUpdater() {
			@Override
			public void OnIdsAvailed(boolean isSupport, String oaid) {
				StringBuilder showInfo =new StringBuilder("设备:");
				showInfo.append(isSupport ? "支持":"不支持");
				showInfo.append("\n MSA：");
				showInfo.append(oaid);
				show(showInfo.toString());
			}
		});
	}

	private void show(final String content) {
		textViewTest.post(new Runnable() {
			@Override
			public void run() {
				textViewTest.setText(content);
			}
		});
	}


}
