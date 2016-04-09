package com.meyao.thingmarket.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import cn.bmob.v3.listener.SaveListener;
import cn.trinea.android.common.util.PreferencesUtils;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Account;

public class AccountAddActivity extends Activity {
	ImageView back;
	ImageView wc;
	EditText zh, je;
	String tp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account_add);

		wc = (ImageView) findViewById(R.id.wc);
		zh = (EditText) findViewById(R.id.zh);
		je = (EditText) findViewById(R.id.je);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tp = getIntent().getStringExtra("tp");

		wc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (zh.getText().toString().trim().equals("")) {
					return;
				}

				Account typeX = new Account();
				typeX.setName(zh.getText().toString());
				typeX.setUid(PreferencesUtils.getString(AccountAddActivity.this, "username"));
				if (je.getText().toString().trim().equals("")) {
					typeX.setMoney(0.0);
				} else {
					typeX.setMoney(Double.parseDouble(je.getText().toString()));
				}
				typeX.save(AccountAddActivity.this, new SaveListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						finish();
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});
			}
		});
	}
}
