package com.meyao.thingmarket.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.SaveListener;

import com.baidu.mobads.SplashAd;
import com.baidu.mobads.SplashAdListener;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.User;
import com.meyao.thingmarket.util.PreferencesUtils;

public class SplashActivity extends Activity {
	private ViewGroup adsParent;
	TextView load;
	private boolean isSplash = false;
	
	private AlphaAnimation alphaAnimation;

	private static final String APPID = "25e644c9c0cdde620aa69eb6aa2a1d8f";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PreferencesUtils.PREFERENCE_NAME = getPackageName();
		// 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
		Bmob.initialize(this, APPID);
		setContentView(R.layout.activity_splash);
		load = (TextView) findViewById(R.id.load);
//		adsParent = (ViewGroup) this.findViewById(R.id.splash_container);
		// setGDTad();
//		showBaiduAD();
		alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(1500);
		load.startAnimation(alphaAnimation);
		// if (!App.getInstance().getLockPatternUtils().savedPatternExists()) {
		 mHandler.sendEmptyMessageDelayed(GO_LOGIN, 1000);
		// }else {
		// mHandler.sendEmptyMessageDelayed(GO_LOCK, 1000);
		// }
		// mHandler.sendEmptyMessage(GO_LOGIN);

	}

	private void showBaiduAD() {
		// TODO Auto-generated method stub
		SplashAdListener listener = new SplashAdListener() {

			@Override
			public void onAdPresent() {
				Log.e("RSplashActivity", "onAdPresent");
			}

			@Override
			public void onAdFailed(String arg0) {
				Log.e("RSplashActivity", "onAdFailed");
				mHandler.sendEmptyMessageDelayed(GO_LOGIN, 1000);
			}

			@Override
			public void onAdDismissed() {
				Log.e("RSplashActivity", "onAdDismissed");
				mHandler.sendEmptyMessage(GO_LOGIN);
			}

			@Override
			public void onAdClick() {
				Log.e("RSplashActivity", "onAdClick");
			}
		};
		String adPlaceId = "2863559"; // 重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
		new SplashAd(this, adsParent, listener, adPlaceId, true);
	}

	private static final int GO_LOCK = 100;
	private static final int GO_LOGIN = 200;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
//			case GO_LOCK:
//				Intent intent1 = new Intent(SplashActivity.this, UnlockGesturePasswordActivity.class);
//				startActivity(intent1);
//				finish();
//				break;
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
