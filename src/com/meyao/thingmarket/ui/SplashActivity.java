package com.meyao.thingmarket.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.bmob.v3.Bmob;
import cn.trinea.android.common.util.PreferencesUtils;

import com.meyao.thingmarket.R;

public class SplashActivity extends Activity {

	private static final String APPID = "25e644c9c0cdde620aa69eb6aa2a1d8f";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PreferencesUtils.PREFERENCE_NAME = getPackageName();
		// 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
		Bmob.initialize(this, APPID);
//		setContentView(R.layout.activity_splash);
//		mHandler.sendEmptyMessageDelayed(GO_LOGIN, 3000);
		mHandler.sendEmptyMessage(GO_LOGIN);
	}

	private static final int GO_HOME = 100;
	private static final int GO_LOGIN = 200;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_HOME:
				break;
			case GO_LOGIN:
				Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}
	};

}
