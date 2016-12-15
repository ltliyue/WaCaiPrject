package com.meyao.thingmarket;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.meyao.thingmarket.util.CustomToast;
import com.meyao.thingmarket.view.CustomProgressDialog;
import com.meyao.thingmarket.view.DialogUtil;

/**
 * 基类
 * 
 * @author MaryLee
 * 
 */
public abstract class BaseActivty extends FragmentActivity {
	protected Context ct;
	protected Application app;

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		app = (Application) getApplication();
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

	// --------
	protected CustomProgressDialog dialog;

	protected void showProgressDialog(String content) {
		if (dialog == null && ct != null) {
			dialog = (CustomProgressDialog) DialogUtil.createProgressDialog(ct, content);
		}
		dialog.show();
	}

	protected void showProgressDialog() {
		if (dialog == null && ct != null) {
			dialog = (CustomProgressDialog) DialogUtil.createProgressDialog(ct);
		}
		dialog.show();
	}

	protected void closeProgressDialog() {
		if (dialog != null)
			dialog.dismiss();
	}
}
