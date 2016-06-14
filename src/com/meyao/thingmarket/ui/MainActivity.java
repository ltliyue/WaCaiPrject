package com.meyao.thingmarket.ui;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.update.BmobUpdateAgent;

import com.baidu.mobstat.StatService;
import com.meyao.thingmarket.AppManager;
import com.meyao.thingmarket.BaseActivty;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.User;
import com.meyao.thingmarket.util.ScreenUtils;

public class MainActivity extends BaseActivty {
	TextView sr, zc, ce;
	RadioButton mx, sq, zh, w;
	LinearLayout top, ddd;
	Button btn_login;
	float zcMoney, srMoney;
	int year, month, chajutianshu;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BmobUpdateAgent.update(this);

		AppManager.getAppManager().addActivity(this);
		user = BmobUser.getCurrentUser(this, User.class);

		initView();
		initListener();

	}

	private void initView() {
		top = (LinearLayout) findViewById(R.id.top);

		mx = (RadioButton) findViewById(R.id.mx);
		sq = (RadioButton) findViewById(R.id.sq);
		zh = (RadioButton) findViewById(R.id.zh);
		w = (RadioButton) findViewById(R.id.w);

		sr = (TextView) findViewById(R.id.sr);
		zc = (TextView) findViewById(R.id.zc);
		ce = (TextView) findViewById(R.id.ce);

		ddd = (LinearLayout) findViewById(R.id.ddd);

		btn_login = (Button) findViewById(R.id.btn_login);

		Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.top6);
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();

		LayoutParams lp;
		lp = top.getLayoutParams();
		lp.width = ScreenUtils.getScreenWidth(MainActivity.this);
		lp.height = height * ScreenUtils.getScreenWidth(MainActivity.this) / width;
		top.setLayoutParams(lp);
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
	}

	@Override
	protected void onResume() {
		super.onResume();
		queryObjectsZC();
		StatService.onResume(this);
	}

	private void initListener() {
		mx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(MainActivity.this, MingxiListActivity.class);
				startActivity(mIntent);
			}
		});
		sq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(MainActivity.this, CharMainActivity.class);
				startActivity(mIntent);
			}
		});
		zh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(MainActivity.this, AccountActivity.class);
				startActivity(mIntent);
			}
		});
		w.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(MainActivity.this, MeActivity.class);
				startActivity(mIntent);
			}
		});
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(MainActivity.this, JYJActivity.class);
				startActivity(mIntent);
			}
		});
	}

	private void queryObjectsZC() {
		BmobQuery<Jz_zc> bmobQuery = new BmobQuery<Jz_zc>();
		bmobQuery.addWhereContains("time", year + "-" + (month < 10 ? "0" + month : month));
		bmobQuery.addWhereEqualTo("temp", "1");
		bmobQuery.addWhereEqualTo("uid", user.getUsername());
		bmobQuery.findObjects(this, new FindListener<Jz_zc>() {

			@Override
			public void onSuccess(List<Jz_zc> object) {
				zcMoney = 0;
				for (int i = 0; i < object.size(); i++) {
					zcMoney += object.get(i).getMoney();
				}
				zc.setText(zcMoney + "");
				queryObjectsSR();
			}

			@Override
			public void onError(int code, String msg) {
			}
		});
	}

	private void queryObjectsSR() {
		BmobQuery<Jz_zc> bmobQuery = new BmobQuery<Jz_zc>();
		bmobQuery.addWhereContains("time", year + "-" + (month < 10 ? "0" + month : month));
		bmobQuery.addWhereEqualTo("temp", "2");
		bmobQuery.addWhereEqualTo("uid", user.getUsername());
		bmobQuery.findObjects(this, new FindListener<Jz_zc>() {

			@Override
			public void onSuccess(List<Jz_zc> object) {
				srMoney = 0;
				for (int i = 0; i < object.size(); i++) {
					srMoney += object.get(i).getMoney();
				}
				sr.setText(srMoney + "");
				DecimalFormat df = new DecimalFormat(".##");
				if (srMoney - zcMoney == 0) {
					ce.setText("0.0");
				} else {
					ce.setText(df.format(srMoney - zcMoney));
				}
			}

			@Override
			public void onError(int code, String msg) {
			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	public void onBackPressed() {
		exitBy2Click();
	}

	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			showToast("再按一次退出程序");
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
		} else {
			AppManager.getAppManager().AppExit(this);
		}
	}
}
