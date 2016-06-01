package com.meyao.thingmarket.ui;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
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
import cn.trinea.android.common.util.ToastUtils;

import com.baidu.mobstat.StatService;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Account;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.SaveMoney;
import com.meyao.thingmarket.model.User;

public class JYJActivity extends Activity {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jyj);
		user = BmobUser.getCurrentUser(this, User.class);
		wc = (ImageView) findViewById(R.id.wc);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
				// TODO Auto-generated method stub
				if (arg1) {
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
				// TODO Auto-generated method stub
				if (arg1) {
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
				// TODO Auto-generated method stub
				if (arg1) {

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
				// TODO Auto-generated method stub
				if (arg1) {
					lin_zh.setVisibility(View.VISIBLE);
					lin_zhft.setVisibility(View.GONE);
					lin_sj.setVisibility(View.GONE);
					lin_zqr.setVisibility(View.VISIBLE);
				}
			}
		});

		lx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
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
		zh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(JYJActivity.this, AccountActivity.class);
				mIntent.putExtra("temp", "0");
				startActivityForResult(mIntent, 2);
			}
		});
		zhf.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(JYJActivity.this, AccountActivity.class);
				mIntent.putExtra("temp", "0");
				startActivityForResult(mIntent, 3);
			}
		});
		zht.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(JYJActivity.this, AccountActivity.class);
				mIntent.putExtra("temp", "0");
				startActivityForResult(mIntent, 4);
			}
		});
		sj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 0;
				dateandtimeHandler.sendMessage(msg);
			}
		});
		fz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 2;
				dateandtimeHandler.sendMessage(msg);
			}
		});

		wc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (money.getText().toString().equals("")) {
					return;
				}
				if (lx.getText().toString().equals("")) {
					return;
				}
				if (a.isChecked()) {
					moneyFloat = Double.parseDouble(money.getText().toString());
					zcSave();
					if (user.isSaveMoney()) {
						updateSaveMoney();
					}
				} else if (b.isChecked()) {
					srSave();
				} else if (c.isChecked()) {
					zzSave();
				}

			}

		});
	}

	private void updateSaveMoney() {
		// TODO Auto-generated method stub
		BmobQuery<SaveMoney> bmobQuery = new BmobQuery<SaveMoney>();
		bmobQuery.addWhereEqualTo("user", user);
		bmobQuery.addWhereEqualTo("isfinish", false);
		bmobQuery.findObjects(JYJActivity.this, new FindListener<SaveMoney>() {

			@Override
			public void onSuccess(List<SaveMoney> arg0) {
				// TODO Auto-generated method stub
				SaveMoney saveMoney = arg0.get(0);
				Double spendtotal = saveMoney.getSpendtotal();
				spendtotal += moneyFloat;
				saveMoney.setSpendtotal(spendtotal);
				saveMoney.update(JYJActivity.this, new UpdateListener() {

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

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void zcSave() {
		// TODO Auto-generated method stub
		if (zh.getText().toString().equals("")) {
			return;
		}
		// if (bus.getText().toString().equals("")) {
		// return;
		// }
		Jz_zc jz_zc = new Jz_zc();

		jz_zc.setMoney(moneyFloat);
		jz_zc.setAccount(zh.getText().toString());
		jz_zc.setTime(sj.getText().toString() + " " + fz.getText().toString());
		jz_zc.setType(lx.getText().toString());
		jz_zc.setBus(bus.getText().toString());
		jz_zc.setTemp("1");
		jz_zc.setBz(bz.getText().toString());
		jz_zc.setUid(PreferencesUtils.getString(JYJActivity.this, "username"));
		jz_zc.save(JYJActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				delMoney(zh.getText().toString());
				JYJActivity.this.finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void srSave() {
		// TODO Auto-generated method stub
		if (zh.getText().toString().equals("")) {
			return;
		}
		Jz_zc jz_zc = new Jz_zc();
		moneyFloat = Double.parseDouble(money.getText().toString());
		jz_zc.setMoney(moneyFloat);
		jz_zc.setAccount(zh.getText().toString());
		jz_zc.setType(lx.getText().toString());
		jz_zc.setTime(sj.getText().toString() + " " + fz.getText().toString());

		jz_zc.setBus(bus.getText().toString());
		jz_zc.setTemp("2");
		jz_zc.setBz(bz.getText().toString());
		jz_zc.setUid(PreferencesUtils.getString(JYJActivity.this, "username"));
		jz_zc.save(JYJActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				addMoney(zh.getText().toString());
				JYJActivity.this.finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void zzSave() {
		// TODO Auto-generated method stub
		if (zhf.getText().toString().equals("")) {
			return;
		}
		if (zht.getText().toString().equals("")) {
			return;
		}
		Jz_zc jz_zc = new Jz_zc();
		moneyFloat = Double.parseDouble(money.getText().toString());
		jz_zc.setMoney(moneyFloat);
		jz_zc.setAccount(zhf.getText().toString());
		jz_zc.setAccount2(zht.getText().toString());
		jz_zc.setTime(sj.getText().toString() + " " + fz.getText().toString());
		jz_zc.setType(lx.getText().toString());
		jz_zc.setBus(bus.getText().toString());
		jz_zc.setTemp("3");
		jz_zc.setBz(bz.getText().toString());
		jz_zc.setUid(PreferencesUtils.getString(JYJActivity.this, "username"));
		jz_zc.save(JYJActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				delMoney(zhf.getText().toString());
				addMoney(zht.getText().toString());
				JYJActivity.this.finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void delMoney(String zhdel) {
		BmobQuery<Account> bmobQuery = new BmobQuery<Account>();
		bmobQuery.addWhereEqualTo("name", zhdel);
		bmobQuery.addWhereEqualTo("uid", PreferencesUtils.getString(JYJActivity.this, "username"));
		bmobQuery.findObjects(JYJActivity.this, new FindListener<Account>() {

			@Override
			public void onSuccess(List<Account> arg0) {
				// TODO Auto-generated method stub
				Account account = arg0.get(0);
				account.setMoney(account.getMoney() - moneyFloat);
				account.update(JYJActivity.this, account.getObjectId(), new UpdateListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						ToastUtils.show(JYJActivity.this, "添加成功");
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						ToastUtils.show(JYJActivity.this, arg1);
					}
				});
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void addMoney(String zhadd) {
		// TODO Auto-generated method stub
		BmobQuery<Account> bmobQuery = new BmobQuery<Account>();
		bmobQuery.addWhereEqualTo("name", zhadd);
		bmobQuery.addWhereEqualTo("uid", PreferencesUtils.getString(JYJActivity.this, "username"));
		bmobQuery.findObjects(JYJActivity.this, new FindListener<Account>() {

			@Override
			public void onSuccess(List<Account> arg0) {
				// TODO Auto-generated method stub
				Account account = arg0.get(0);
				account.setMoney(account.getMoney() + moneyFloat);
				account.update(JYJActivity.this, account.getObjectId(), new UpdateListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						ToastUtils.show(JYJActivity.this, "添加成功");
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						ToastUtils.show(JYJActivity.this, arg1);
					}
				});
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
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

	/**
	 * 处理日期和时间控件的Handler
	 */
	Handler dateandtimeHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				showDialog(1);
				break;
			case 2:
				showDialog(3);
				break;
			}
		}

	};
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
