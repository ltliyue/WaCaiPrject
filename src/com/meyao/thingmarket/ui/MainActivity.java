package com.meyao.thingmarket.ui;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.update.BmobUpdateAgent;

import com.baidu.mobads.AdView;
import com.baidu.mobads.AdViewListener;
import com.baidu.mobads.AppActivity;
import com.baidu.mobads.AppActivity.ActionBarColorTheme;
import com.baidu.mobstat.StatService;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.meyao.thingmarket.AppManager;
import com.meyao.thingmarket.BaseActivty;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.User;
import com.meyao.thingmarket.util.Constants;
import com.meyao.thingmarket.util.ScreenUtils;

public class MainActivity extends BaseActivty {
	TextView sr, zc, ce;
	RadioButton mx, sq, zh, w;
	LinearLayout top, ddd;
	Button btn_login;
	float zcMoney, srMoney;
	int year, month, chajutianshu;
	User user;

	@ViewInject(R.id.adcontainer)
	ViewGroup mAdContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewUtils.inject(this);

		BmobUpdateAgent.update(this);
		BmobInstallation.getCurrentInstallation(this).save();
		AppManager.getAppManager().addActivity(this);
		creatBannerAD();
		user = BmobUser.getCurrentUser(this, User.class);
		initView();
		initListener();

	}

	AdView adView;

	private void creatBannerAD() {
		// TODO Auto-generated method stub
		AppActivity.setActionBarColorTheme(ActionBarColorTheme.ACTION_BAR_WHITE_THEME);
		// 另外，也可设置动作栏中单个元素的颜色, 颜色参数为四段制，0xFF(透明度, 一般填FF)DE(红)DA(绿)DB(蓝)
		// AppActivity.getActionBarColorTheme().set[Background|Title|Progress|Close]Color(0xFFDEDADB);

		// 创建广告View
		String adPlaceId = "2863555"; // 重要：请填上您的广告位ID，代码位错误会导致无法请求到广告
		adView = new AdView(this, adPlaceId);
		// 设置监听器
		adView.setListener(new AdViewListener() {
			public void onAdSwitch() {
				Log.w("", "onAdSwitch");
			}

			public void onAdShow(JSONObject info) {
				// 广告已经渲染出来
				Log.w("", "onAdShow " + info.toString());
			}

			public void onAdReady(AdView adView) {
				// 资源已经缓存完毕，还没有渲染出来
				Log.w("", "onAdReady " + adView);
			}

			public void onAdFailed(String reason) {
				Log.w("", "onAdFailed " + reason);
			}

			public void onAdClick(JSONObject info) {
				// Log.w("", "onAdClick " + info.toString());

			}

			@Override
			public void onAdClose(JSONObject arg0) {
				Log.w("", "onAdClose");
			}
		});
		// 将adView添加到父控件中(注：该父控件不一定为您的根控件，只要该控件能通过addView能添加广告视图即可)
		RelativeLayout.LayoutParams rllp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		rllp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		mAdContainer.addView(adView, rllp);

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

	/**
	 * Activity销毁时，销毁adView
	 */
	@Override
	protected void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}
}
