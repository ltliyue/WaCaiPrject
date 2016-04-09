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
import com.meyao.thingmarket.model.TypeD;
import com.meyao.thingmarket.model.TypeX;

public class TypeAddActivity extends Activity {
	ImageView back;
	ImageView wc;
	EditText edit;
	String tp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.type_add);

		wc = (ImageView) findViewById(R.id.wc);
		edit = (EditText) findViewById(R.id.edit);
		edit.setFocusable(true);
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
				if (edit.getText().toString().trim().equals("")) {
					return;
				}
				if (tp.equals("0")) {
					TypeX typeX = new TypeX();
					typeX.setName(edit.getText().toString());
					typeX.setUid(PreferencesUtils.getString(TypeAddActivity.this, "username"));
					typeX.setPid(getIntent().getStringExtra("pid"));
					typeX.save(TypeAddActivity.this, new SaveListener() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub

						}
					});
				} else {
					TypeD typeD = new TypeD();
					typeD.setName(edit.getText().toString());
					typeD.setUid(PreferencesUtils.getString(TypeAddActivity.this, "username"));
					typeD.setType(tp);
					typeD.save(TypeAddActivity.this, new SaveListener() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub

						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub

						}
					});

				}
				finish();
			}
		});
	}
}
