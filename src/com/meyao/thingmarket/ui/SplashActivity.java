package com.meyao.thingmarket.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.trinea.android.common.util.PreferencesUtils;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.lock.App;
import com.meyao.thingmarket.lock.UnlockGesturePasswordActivity;
import com.meyao.thingmarket.model.User;

public class SplashActivity extends Activity {

	private static final String APPID = "25e644c9c0cdde620aa69eb6aa2a1d8f";
//571ab73267e58e38ca0000ca
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PreferencesUtils.PREFERENCE_NAME = getPackageName();
		// 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
		Bmob.initialize(this, APPID);
		setContentView(R.layout.activity_splash);
		if (!App.getInstance().getLockPatternUtils().savedPatternExists()) {
			mHandler.sendEmptyMessageDelayed(GO_LOGIN, 1000);
		}else {
			mHandler.sendEmptyMessageDelayed(GO_LOCK, 1000);
		}
//		mHandler.sendEmptyMessage(GO_LOGIN);
	}

	private static final int GO_LOCK = 100;
	private static final int GO_LOGIN = 200;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case GO_LOCK:
				Intent intent1 = new Intent(SplashActivity.this, UnlockGesturePasswordActivity.class);
				startActivity(intent1);
				finish();
				break;
			case GO_LOGIN:
				if (PreferencesUtils.getBoolean(SplashActivity.this, "autologin", false)) {
					autoLogin();
					return;
				}
				Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}
	};
	private void autoLogin() {
		User bu2 = new User();
		bu2.setUsername(PreferencesUtils.getString(this, "username"));
		bu2.setPassword(PreferencesUtils.getString(this, "password"));
		bu2.login(this, new SaveListener() {
			@Override
			public void onSuccess() {
				// 跳转到主页
				Intent toHome = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(toHome);
				finish();
			}

			@Override
			public void onFailure(int arg0, String msg) {
				Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
}
