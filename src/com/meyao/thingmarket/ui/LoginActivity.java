package com.meyao.thingmarket.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.listener.SaveListener;
import cn.trinea.android.common.util.PreferencesUtils;

import com.meyao.thingmarket.BaseActivty;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.User;
import com.meyao.thingmarket.util.Util;

/**
 * 登陆界面
 * 
 * @date 2014-4-24
 * @author Stone
 */
public class LoginActivity extends BaseActivty implements OnClickListener {

	@SuppressWarnings("unused")
	private static final String TAG = "LoginActicity";

	private CheckBox check1;
	private CheckBox check2;

	private Button btnLogin;
	private Button btnReg;
	// private Button btnResetPsd;
	private EditText etUsername;
	private EditText etPassword;

	private String username;
	private String password;

	private TextView mUserInfo;
	private ImageView mUserLogo;
	private ImageView mNewLoginButton;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				mUserInfo.setVisibility(android.view.View.VISIBLE);
				mUserInfo.setText(msg.getData().getString("nickname"));
			} else if (msg.what == 1) {
				Bitmap bitmap = (Bitmap) msg.obj;
				mUserLogo.setImageBitmap(bitmap);
				mUserLogo.setVisibility(android.view.View.VISIBLE);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);
		check1 = (CheckBox) findViewById(R.id.check1);
		check2 = (CheckBox) findViewById(R.id.check2);

		btnLogin = (Button) findViewById(R.id.btn_login);
		btnReg = (Button) findViewById(R.id.btn_register);
		// btnResetPsd = (Button) findViewById(R.id.btn_reset_psd);

		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);

		btnLogin.setOnClickListener(this);
		btnReg.setOnClickListener(this);
		// btnResetPsd.setOnClickListener(this);

		mUserInfo = (TextView) findViewById(R.id.user_nickname);
		mUserLogo = (ImageView) findViewById(R.id.user_logo);
		mNewLoginButton = (ImageView) findViewById(R.id.new_login_btn);
		mNewLoginButton.setOnClickListener(this);

		check1.setChecked(PreferencesUtils.getBoolean(this, "savepwd", true));
		check1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					PreferencesUtils.putBoolean(LoginActivity.this, "savepwd", true);
				} else {
					check2.setChecked(false);
					PreferencesUtils.putBoolean(LoginActivity.this, "savepwd", false);
				}
			}
		});
		check2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					check1.setChecked(true);
					PreferencesUtils.putBoolean(LoginActivity.this, "autologin", true);
				} else {
					PreferencesUtils.putBoolean(LoginActivity.this, "autologin", false);
				}
			}
		});
		etUsername.setText(PreferencesUtils.getString(this, "username"));
		if (PreferencesUtils.getBoolean(this, "savepwd", true)) {
			getUserInfo();
		}
	}

	private void getUserInfo() {

		etPassword.setText(PreferencesUtils.getString(this, "password"));
	}

	// 保存用户的登陆记录
	private void saveUserInfo(String username, String password) {
		PreferencesUtils.putString(this, "username", username);
		PreferencesUtils.putString(this, "password", password);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 登陆
		case R.id.btn_login:

			username = etUsername.getText().toString();
			password = etPassword.getText().toString();

			if (!Util.isNetworkConnected(this)) {
				showToast("未检查到网络");
//			} else if (username.equals("") || password.equals("")) {
//				showToast("请输入账号和密码~");
//				break;
			} else {
				showLonding();
				// loadingView.setVisibility(View.VISIBLE);
				User bu2 = new User();
				bu2.setUsername(username);
				bu2.setPassword(password);
				bu2.login(this, new SaveListener() {
					@Override
					public void onSuccess() {
						// 保存用户信息
						saveUserInfo(username, password);
						// 跳转到主页
						Intent toHome = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(toHome);
						finish();
					}

					@Override
					public void onFailure(int arg0, String msg) {
//						showToast("用户名或密码错误");
						showToast(msg);
						missLonding();
					}
				});
			}
			break;

		// case R.id.btn_reset_psd:
		// Intent toResetPsdActivity = new Intent(LoginActivity.this,
		// ResetPsdActivity.class);
		// startActivity(toResetPsdActivity);
		// break;

		case R.id.btn_register:
			Intent toReg = new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(toReg);
			break;
		default:
			break;

		}
	}

}
