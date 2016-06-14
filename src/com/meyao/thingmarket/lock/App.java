package com.meyao.thingmarket.lock;

import com.meyao.thingmarket.CrashHandler;

import android.app.Application;

public class App extends Application {
	private static App mInstance;
	private LockPatternUtils mLockPatternUtils;

	public static App getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		mLockPatternUtils = new LockPatternUtils(this);
//		CrashHandler crashHandler = CrashHandler.getInstance();  
//        crashHandler.init(getApplicationContext());  
	}

	public LockPatternUtils getLockPatternUtils() {
		return mLockPatternUtils;
	}
}
