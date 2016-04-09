package com.meyao.thingmarket.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import cn.trinea.android.common.util.PreferencesUtils;

import com.meyao.thingmarket.AppManager;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.lock.App;
import com.meyao.thingmarket.lock.CreateGesturePasswordActivity;
import com.meyao.thingmarket.lock.GuideGesturePasswordActivity;

public class MeActivity extends Activity {

	TextView yhm;
	ImageView back;
	LinearLayout lin_w, lin_zc, lin_sr, sqxx, xgmm, gy, tajs;
	private ToggleButton tbtn_msg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.me);
		AppManager.getAppManager().addActivity(this);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		yhm = (TextView) findViewById(R.id.yhm);
		yhm.setText("用户名：" + PreferencesUtils.getString(this, "username"));
		lin_w = (LinearLayout) findViewById(R.id.lin_w);
		lin_zc = (LinearLayout) findViewById(R.id.lin_zc);
		lin_sr = (LinearLayout) findViewById(R.id.lin_sr);
		sqxx = (LinearLayout) findViewById(R.id.sqxx);
		xgmm = (LinearLayout) findViewById(R.id.xgmm);
		gy = (LinearLayout) findViewById(R.id.gy);
		tajs = (LinearLayout) findViewById(R.id.tajs);
		tbtn_msg = (ToggleButton) findViewById(R.id.tbtn_msg);

		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tajs.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(MeActivity.this, GuideGesturePasswordActivity.class);
				startActivity(mIntent);
			}
		});
		gy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(MeActivity.this, AboutActivity.class);
				startActivity(mIntent);
			}
		});
		sqxx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(MeActivity.this, MyCommunityListActivity.class);
				startActivity(mIntent);
			}
		});
		xgmm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(MeActivity.this, UpdatePwdActivity.class);
				startActivity(mIntent);
			}
		});
		lin_zc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(MeActivity.this, TypeList.class);
				mIntent.putExtra("type", "1");
				startActivity(mIntent);
			}
		});
		lin_sr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(MeActivity.this, TypeList.class);
				mIntent.putExtra("type", "2");
				startActivity(mIntent);
			}
		});
		tbtn_msg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean state) {
				if (tbtn_msg.isChecked()&&!App.getInstance().getLockPatternUtils().savedPatternExists()) {
					// Intent mIntent = new Intent(MeActivity.this,
					// GuideGesturePasswordActivity.class);
					Intent mIntent = new Intent(MeActivity.this, CreateGesturePasswordActivity.class);
					startActivity(mIntent);
				} else {
					App.getInstance().getLockPatternUtils().clearLock();
				}
				// PreferencesUtils.putBoolean(MoreActivity.this,
				// "messageState", state);
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		tbtn_msg.setChecked(App.getInstance().getLockPatternUtils().savedPatternExists());
	}
}
