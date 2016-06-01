package com.meyao.thingmarket.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.listener.SaveListener;
import cn.trinea.android.common.util.ToastUtils;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.FKBean;
import com.meyao.thingmarket.util.Util;

/**
 * 反馈信息
 * 
 * @author Administrator
 * 
 */
@SuppressLint("NewApi")
public class Tab3_FKActivity extends Activity {

	private ImageView btn_back;
	private TextView setting;
	private EditText edit_description, edit_relationType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fb_main_layout);
		initView();
		initListener();
	}

	private void initView() {
		setting = (TextView) findViewById(R.id.setting);
		setting.setText("反馈");
		btn_back = (ImageView) findViewById(R.id.btn_back);
		edit_description = (EditText) findViewById(R.id.edit_description);
		edit_relationType = (EditText) findViewById(R.id.edit_relationType);
	}

	private void initListener() {
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		setting.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if ("".equals(edit_description.getText().toString().trim())) {
					edit_description.setError("反馈信息不能为空！");
					return;
				}
				FKBean fkBean = new FKBean();
				fkBean.setAppName("OneAccount");
				fkBean.setFkInfo(edit_description.getText().toString());
				fkBean.setRelationType(edit_relationType.getText().toString());
				fkBean.setVersion("版本：v"+Util.getAppVersionName(Tab3_FKActivity.this));
				fkBean.save(Tab3_FKActivity.this, new SaveListener() {

					@Override
					public void onSuccess() {
						ToastUtils.show(Tab3_FKActivity.this, "反馈成功，感谢您提出的宝贵意见~");
						finish();
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						return;
					}
				});
			}
		});
	}
}
