package com.meyao.thingmarket.ui;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.util.Util;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutActivity extends Activity {
	ImageView back;
	TextView tv_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		back = (ImageView) findViewById(R.id.back);
		tv_version = (TextView) findViewById(R.id.tv_version);
		tv_version.setText("版本：v"+Util.getAppVersionName(this));
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
