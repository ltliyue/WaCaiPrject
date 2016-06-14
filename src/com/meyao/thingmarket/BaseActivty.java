package com.meyao.thingmarket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.meyao.thingmarket.lock.App;
import com.meyao.thingmarket.util.CustomToast;

/**
 * 基类
 * 
 * @author MaryLee
 * 
 */
public abstract class BaseActivty extends FragmentActivity {

	protected Context ct;
	protected App app;

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = (App) getApplication();
		ct = this;
	}

	protected void showToast(String msg) {
		CustomToast customToast = new CustomToast(ct, msg);
		customToast.show();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	protected static ProgressDialog mProgressDialog = null;

	/**
	 * show loading
	 */
	public void showLonding() {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(ct);
			mProgressDialog.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if (keyCode == event.KEYCODE_BACK) {
						mProgressDialog.dismiss();
						return true;
					}
					return false;
				}
			});
			mProgressDialog.setCancelable(false);
			mProgressDialog.show();
			mProgressDialog.setContentView(R.layout.layout_loading);
		} else {
			mProgressDialog.show();
		}
	}

	public void missLonding() {
		if (mProgressDialog != null)
			mProgressDialog.dismiss();
	}

	protected void getInfoInThread() {
	}
}
