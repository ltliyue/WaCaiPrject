package com.meyao.thingmarket.ui;

import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.trinea.android.common.util.PreferencesUtils;

import com.baidu.mobstat.StatService;
import com.lidroid.xutils.util.LogUtils;
import com.meyao.thingmarket.BaseActivty;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Account;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.User;

public class JYJActivity extends BaseActivty {

	LinearLayout lin_zh, lin_zhft, lin_sj, lin_zqr;
	RadioButton a, b, c, d;
	TextView lx, zh, sj, fz, zhf, zht;
	EditText money, bus, bz;
	ImageView wc;
	Double moneyFloat;
	ImageView back;
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;
	User user;

	int WATYPE = 1;

	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				showDialog(1);
				break;
			case 2:
				showDialog(3);
				break;
			case 101:
				lx.setText("");
				zh.setText("");
				// sj.setText("");
				// fz.setText("");
				zhf.setText("");
				zht.setText("");
				money.setText("");
				bus.setText("");
				bz.setText("");

				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jyj);
		user = BmobUser.getCurrentUser(this, User.class);
		wc = (ImageView) findViewById(R.id.wc);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		a = (RadioButton) findViewById(R.id.a);
		b = (RadioButton) findViewById(R.id.b);
		c = (RadioButton) findViewById(R.id.c);
		d = (RadioButton) findViewById(R.id.d);

		lin_zh = (LinearLayout) findViewById(R.id.lin_zh);
		lin_zhft = (LinearLayout) findViewById(R.id.lin_zhft);
		lin_sj = (LinearLayout) findViewById(R.id.lin_sj);
		lin_zqr = (LinearLayout) findViewById(R.id.lin_zqr);

		lx = (TextView) findViewById(R.id.lx);
		zh = (TextView) findViewById(R.id.zh);
		sj = (TextView) findViewById(R.id.sj);
		fz = (TextView) findViewById(R.id.fz);
		zhf = (TextView) findViewById(R.id.zhf);
		zht = (TextView) findViewById(R.id.zht);

		money = (EditText) findViewById(R.id.money);
		bus = (EditText) findViewById(R.id.bus);
		bz = (EditText) findViewById(R.id.bz);

		lin_zh.setVisibility(View.VISIBLE);
		lin_zhft.setVisibility(View.GONE);
		lin_sj.setVisibility(View.VISIBLE);
		lin_zqr.setVisibility(View.GONE);

		final Calendar ca = Calendar.getInstance();
		mYear = ca.get(Calendar.YEAR);
		mMonth = ca.get(Calendar.MONTH);
		mDay = ca.get(Calendar.DAY_OF_MONTH);

		mHour = ca.get(Calendar.HOUR_OF_DAY);
		mMinute = ca.get(Calendar.MINUTE);

		setDateTime();
		setTimeOfDay();

		a.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					WATYPE = 1;
					lin_zh.setVisibility(View.VISIBLE);
					lin_zhft.setVisibility(View.GONE);
					lin_sj.setVisibility(View.VISIBLE);
					lin_zqr.setVisibility(View.GONE);
				}
			}
		});
		b.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					WATYPE = 2;
					lin_zh.setVisibility(View.VISIBLE);
					lin_zhft.setVisibility(View.GONE);
					lin_sj.setVisibility(View.GONE);
					lin_zqr.setVisibility(View.GONE);
				}
			}
		});
		c.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					WATYPE = 3;
					lin_zh.setVisibility(View.GONE);
					lin_zhft.setVisibility(View.VISIBLE);
					lin_sj.setVisibility(View.GONE);
					lin_zqr.setVisibility(View.GONE);
				}
			}
		});
		d.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
					WATYPE = 4;
					lin_zh.setVisibility(View.VISIBLE);
					lin_zhft.setVisibility(View.GONE);
					lin_sj.setVisibility(View.GONE);
					lin_zqr.setVisibility(View.VISIBLE);
				}
			}
		});

		lx.setOnClickListener(new OnClickListener() {// 类型

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(JYJActivity.this, TypeList.class);
				if (a.isChecked()) {
					mIntent.putExtra("type", "1");
					mIntent.putExtra("temp", "0");
				} else if (b.isChecked()) {
					mIntent.putExtra("type", "2");
					mIntent.putExtra("temp", "0");
				} else if (c.isChecked()) {
					mIntent.putExtra("type", "1");
					mIntent.putExtra("temp", "0");
				}
				startActivityForResult(mIntent, 2);
			}
		});
		zh.setOnClickListener(new OnClickListener() {// 账户

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(JYJActivity.this, AccountActivity.class);
				mIntent.putExtra("temp", "0");
				startActivityForResult(mIntent, 2);
			}
		});
		zhf.setOnClickListener(new OnClickListener() {// 支出from

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(JYJActivity.this, AccountActivity.class);
				mIntent.putExtra("temp", "0");
				startActivityForResult(mIntent, 3);
			}
		});
		zht.setOnClickListener(new OnClickListener() {// 支出to

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(JYJActivity.this, AccountActivity.class);
				mIntent.putExtra("temp", "0");
				startActivityForResult(mIntent, 4);
			}
		});
		sj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mHandler.sendEmptyMessage(0);
			}
		});
		fz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mHandler.sendEmptyMessage(2);
			}
		});

		wc.setOnClickListener(new OnClickListener() {// 完成

			@Override
			public void onClick(View arg0) {
				if (money.getText().toString().equals("")) {
					return;
				}
				if (lx.getText().toString().equals("")) {
					return;
				}
				Jz_zc jz_zc;
				if (WATYPE == 3) {
					if (zhf.getText().toString().equals("")) {
						showToast("信息不完整");
						return;
					}
					if (zht.getText().toString().equals("")) {
						showToast("信息不完整");
						return;
					}
					jz_zc = new Jz_zc();
					jz_zc.setAccount(zhf.getText().toString());
					jz_zc.setAccount2(zht.getText().toString());
				} else {
					if (zh.getText().toString().equals("")) {
						showToast("信息不完整");
						return;
					}
					jz_zc = new Jz_zc();
					jz_zc.setAccount(zh.getText().toString());
				}
				moneyFloat = Double.parseDouble(money.getText().toString());
				saveMoneyRecord(jz_zc);
			}
		});
	}

	/**
	 * 保存记录
	 * 
	 * @param jz_zc
	 */
	private void saveMoneyRecord(Jz_zc jz_zc) {
		jz_zc.setMoney(moneyFloat);
		jz_zc.setType(lx.getText().toString());
		jz_zc.setTime(sj.getText().toString() + " " + fz.getText().toString());
		jz_zc.setBus(bus.getText().toString());
		jz_zc.setTemp(WATYPE + "");
		jz_zc.setBz(bz.getText().toString());
		jz_zc.setUid(PreferencesUtils.getString(JYJActivity.this, "username"));

		jz_zc.save(JYJActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				if (WATYPE == 3) {
					saveMoneyAccountA(zhf.getText().toString(), zht.getText().toString());
				} else {
					saveMoneyAccount(zh.getText().toString());
				}
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				missLonding();
				showToast(arg1);
			}
		});
	}

	/**
	 * 保存账户的收入支出
	 * 
	 * @param zhdel
	 */
	private void saveMoneyAccount(String zhdel) {
		BmobQuery<Account> bmobQuery = new BmobQuery<Account>();
		bmobQuery.addWhereEqualTo("name", zhdel);
		bmobQuery.addWhereEqualTo("uid", PreferencesUtils.getString(JYJActivity.this, "username"));
		bmobQuery.findObjects(JYJActivity.this, new FindListener<Account>() {

			@Override
			public void onSuccess(List<Account> arg0) {
				Account account = arg0.get(0);
				switch (WATYPE) {
				case 1:
					account.setMoney(account.getMoney() - moneyFloat);
					break;
				case 2:
					account.setMoney(account.getMoney() + moneyFloat);
					break;
				default:
					break;
				}
				account.update(JYJActivity.this, new UpdateListener() {
					@Override
					public void onSuccess() {
						mHandler.sendEmptyMessage(101);
						// showToast("添加成功");
						missLonding();
						showDialog();
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						missLonding();
						showToast(arg1);
					}
				});
			}

			@Override
			public void onError(int arg0, String arg1) {
				showToast(arg1);
			}
		});

	}

	/**
	 * 保存转账的账户
	 * 
	 * @param from
	 * @param to
	 */
	private void saveMoneyAccountA(String from, final String to) {
		BmobQuery<Account> bmobQuery = new BmobQuery<Account>();
		bmobQuery.addWhereEqualTo("name", from);
		bmobQuery.addWhereEqualTo("uid", PreferencesUtils.getString(JYJActivity.this, "username"));
		bmobQuery.findObjects(JYJActivity.this, new FindListener<Account>() {

			@Override
			public void onSuccess(List<Account> arg0) {
				Account account = arg0.get(0);
				account.setMoney(account.getMoney() - moneyFloat);
				account.update(JYJActivity.this, account.getObjectId(), new UpdateListener() {
					@Override
					public void onSuccess() {
						saveMoneyAccountB(to);
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						missLonding();
						showToast(arg1);
					}
				});
			}

			@Override
			public void onError(int arg0, String arg1) {
				showToast(arg1);
			}
		});

	}

	private void saveMoneyAccountB(String to) {
		BmobQuery<Account> bmobQuery = new BmobQuery<Account>();
		bmobQuery.addWhereEqualTo("name", to);
		bmobQuery.addWhereEqualTo("uid", PreferencesUtils.getString(JYJActivity.this, "username"));
		bmobQuery.findObjects(JYJActivity.this, new FindListener<Account>() {

			@Override
			public void onSuccess(List<Account> arg0) {
				Account account = arg0.get(0);
				account.setMoney(account.getMoney() + moneyFloat);
				account.update(JYJActivity.this, account.getObjectId(), new UpdateListener() {
					@Override
					public void onSuccess() {
						mHandler.sendEmptyMessage(101);
						// showToast("添加成功");
						missLonding();
						showDialog();
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						missLonding();
						showToast(arg1);
					}
				});
			}

			@Override
			public void onError(int arg0, String arg1) {
				showToast(arg1);
			}
		});

	}

	private void showDialog() {
		final AlertDialog dialog = new AlertDialog.Builder(ct).create();
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.alertdialog);
		TextView tv_title = (TextView) window.findViewById(R.id.name);
		tv_title.setText("添加成功~ 是否继续添加？");
		Button button1 = (Button) window.findViewById(R.id.btn1);
		Button button2 = (Button) window.findViewById(R.id.btn2);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				finish();
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2) {
			switch (resultCode) {
			case 100:
				zh.setText(data.getExtras().getString("accountname"));
				break;
			case 200:
				lx.setText(data.getExtras().getString("typename"));
				break;

			default:
				break;
			}
		} else if (requestCode == 3) {
			zhf.setText(data.getExtras().getString("accountname"));
		} else {
			zht.setText(data.getExtras().getString("accountname"));
		}
	}

	/**
	 * 设置日期
	 */
	private void setDateTime() {
		final Calendar c = Calendar.getInstance();

		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		updateDateDisplay();
	}

	/**
	 * 更新日期显示
	 */
	private void updateDateDisplay() {
		sj.setText(new StringBuilder().append(mYear).append("-")
				.append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1)).append("-")
				.append((mDay < 10) ? "0" + mDay : mDay));
	}

	/**
	 * 日期控件的事件
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;

			updateDateDisplay();
		}
	};

	/**
	 * 设置时间
	 */
	private void setTimeOfDay() {
		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		updateTimeDisplay();
	}

	/**
	 * 更新时间显示
	 */
	private void updateTimeDisplay() {
		fz.setText(new StringBuilder().append(mHour).append(":").append((mMinute < 10) ? "0" + mMinute : mMinute));
	}

	/**
	 * 时间控件事件
	 */
	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			mHour = hourOfDay;
			mMinute = minute;

			updateTimeDisplay();
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 1:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		case 3:
			return new TimePickerDialog(this, mTimeSetListener, mHour, mMinute, true);
		}
		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case 1:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
			break;
		case 3:
			((TimePickerDialog) dialog).updateTime(mHour, mMinute);
			break;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		StatService.onResume(this);
	}
}
