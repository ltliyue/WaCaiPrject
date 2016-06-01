package com.meyao.thingmarket.ui;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.update.BmobUpdateAgent;

import com.baidu.mobstat.StatService;
import com.meyao.thingmarket.AppManager;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.SaveMoney;
import com.meyao.thingmarket.model.User;
import com.meyao.thingmarket.util.CheckDateNum;
import com.meyao.thingmarket.util.ScreenUtils;

public class MainActivity extends Activity {
	TextView sr, zc, ce, mbje, kyhf, syts;
	RadioButton mx, sq, zh, cq, w;
	LinearLayout top, ddd;
	// inearLayout eee, fff;
	Button btn_login;
	float zcMoney, srMoney;
	int year, month, chajutianshu;
	private int mYear;
	private int mMonth;
	private int mDay;
	User user;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				int shengyutianshu = 0;
				SaveMoney saveMoney = (SaveMoney) msg.obj;
				mbje.setText(saveMoney.getSavemoney() + "");
				try {
					shengyutianshu = CheckDateNum.daysBetween(mYear + "-" + mMonth + "-" + mDay,
							saveMoney.getSavetime());
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				DecimalFormat df = new DecimalFormat(".##");
				kyhf.setText(df.format((saveMoney.getSavesr() - saveMoney.getSavemoney() - saveMoney.getSpendtotal())
						/ shengyutianshu));
				syts.setText(shengyutianshu + "");
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		BmobUpdateAgent.update(this);
		
		AppManager.getAppManager().addActivity(this);
		user = BmobUser.getCurrentUser(this, User.class);

		Calendar ca = Calendar.getInstance();
		mYear = ca.get(Calendar.YEAR);
		mMonth = ca.get(Calendar.MONTH) + 1;
		mDay = ca.get(Calendar.DAY_OF_MONTH);

		initView();
		initListener();

	}

	private void initView() {
		top = (LinearLayout) findViewById(R.id.top);

		mx = (RadioButton) findViewById(R.id.mx);
		sq = (RadioButton) findViewById(R.id.sq);
		zh = (RadioButton) findViewById(R.id.zh);
		cq = (RadioButton) findViewById(R.id.cq);
		w = (RadioButton) findViewById(R.id.w);

		sr = (TextView) findViewById(R.id.sr);
		zc = (TextView) findViewById(R.id.zc);
		ce = (TextView) findViewById(R.id.ce);

		mbje = (TextView) findViewById(R.id.mbje);
		kyhf = (TextView) findViewById(R.id.kyhf);
		syts = (TextView) findViewById(R.id.syts);

		ddd = (LinearLayout) findViewById(R.id.ddd);
		// eee = (LinearLayout) findViewById(R.id.eee);
		// fff = (LinearLayout) findViewById(R.id.fff);

		btn_login = (Button) findViewById(R.id.btn_login);

		Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.main_top);
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
		querySaveMoney();
		StatService.onResume(this);
//		MobclickAgent.onResume(this);
	}

	private void initListener() {
		mx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(MainActivity.this, MingxiListActivity.class);
				startActivity(mIntent);
			}
		});
		sq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent mIntent = new Intent(MainActivity.this,
				// CommunityListActivity.class);
				Intent mIntent = new Intent(MainActivity.this, CharMainActivity.class);
				startActivity(mIntent);
			}
		});
		zh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(MainActivity.this, AccountActivity.class);
				startActivity(mIntent);
			}
		});
		cq.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				BmobQuery<User> bmobQuery = new BmobQuery<User>();
				bmobQuery.addWhereEqualTo("objectId", user.getObjectId());
				bmobQuery.findObjects(MainActivity.this, new FindListener<User>() {

					@Override
					public void onSuccess(List<User> arg0) {
						// TODO Auto-generated method stub
						if (arg0.get(0).isSaveMoney()) {
							Intent mIntent = new Intent(MainActivity.this, SaveMain.class);
							startActivity(mIntent);
						} else {
							Intent mIntent = new Intent(MainActivity.this, SaveMoneyActivity.class);
							startActivity(mIntent);
						}
					}

					@Override
					public void onError(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});

			}
		});
		w.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(MainActivity.this, MeActivity.class);
				startActivity(mIntent);
			}
		});

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
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
				// BigDecimal b1 = new BigDecimal(Float.toString(srMoney));
				// BigDecimal b2 = new BigDecimal(Float.toString(zcMoney));
				// ce.setText(b1.subtract(b2).floatValue() + "");

			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void querySaveMoney() {
		// TODO Auto-generated method stub
		BmobQuery<SaveMoney> bmobQuery = new BmobQuery<SaveMoney>();
		bmobQuery.addWhereEqualTo("user", user);
		bmobQuery.addWhereEqualTo("isfinish", false);
		bmobQuery.findObjects(MainActivity.this, new FindListener<SaveMoney>() {

			@Override
			public void onSuccess(List<SaveMoney> arg0) {
				// TODO Auto-generated method stub
				if (arg0 != null && arg0.size() > 0) {
					try {
						// System.out.println("11111::"+CheckDateNum.daysBetween(mYear
						// + "-" + mMonth + "-" + mDay, arg0.get(0)
						// .getSavetime()));
						chajutianshu = CheckDateNum.daysBetween(mYear + "-" + mMonth + "-" + mDay, arg0.get(0)
								.getSavetime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (chajutianshu <= 0) {

						user.setSaveMoney(false);
						user.update(MainActivity.this, new UpdateListener() {

							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub

							}
						});

						SaveMoney saveMoney = arg0.get(0);
						saveMoney.setIsfinish(true);
						saveMoney.update(MainActivity.this, new UpdateListener() {

							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onFailure(int arg0, String arg1) {
								// TODO Auto-generated method stub

							}
						});

						return;
					}
					ddd.setVisibility(View.VISIBLE);
					// eee.setVisibility(View.VISIBLE);
					// fff.setVisibility(View.VISIBLE);
					Message message = new Message();
					message.obj = arg0.get(0);
					message.what = 1;
					mHandler.sendMessage(message);
				}
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause (this);
	}
}
