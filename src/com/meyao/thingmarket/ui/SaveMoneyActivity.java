package com.meyao.thingmarket.ui;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.trinea.android.common.util.PreferencesUtils;
import cn.trinea.android.common.util.ToastUtils;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.SaveMoney;
import com.meyao.thingmarket.model.TypeD;
import com.meyao.thingmarket.model.User;
import com.meyao.thingmarket.util.CheckDateNum;

public class SaveMoneyActivity extends Activity {
	ImageView back;
	TextView sj;
	EditText savemoney, sr;
	ImageView wc;
	Double moneyFloat, srMoney;
	User user;

	private int mYear, todayYear;
	private int mMonth, todayMonth;
	private int mDay, todayDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		user = BmobUser.getCurrentUser(this, User.class);
		
		setContentView(R.layout.save_moneyj);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		wc = (ImageView) findViewById(R.id.wc);

		sj = (TextView) findViewById(R.id.sj);

		savemoney = (EditText) findViewById(R.id.savemoney);
		sr = (EditText) findViewById(R.id.sr);

		final Calendar ca = Calendar.getInstance();
		mYear = ca.get(Calendar.YEAR);
		mMonth = ca.get(Calendar.MONTH);
		mDay = ca.get(Calendar.DAY_OF_MONTH);
		todayYear = mYear;
		todayMonth = mMonth + 1;
		todayDay = mDay;

		setDateTime();
		sj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Message msg = new Message();
				msg.what = 0;
				dateandtimeHandler.sendMessage(msg);
			}
		});
		wc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (savemoney.getText().toString().equals("")) {
					return;
				}
				if (sr.getText().toString().equals("")) {
					return;
				}
				moneyFloat = Double.parseDouble(savemoney.getText().toString());
				srMoney = Double.parseDouble(sr.getText().toString());
				if (moneyFloat < 0 || srMoney < 0) {
					return;
				}
				if (srMoney < moneyFloat) {
					ToastUtils.show(SaveMoneyActivity.this, "收入小于目标金额");
					return;
				}
				try {
					if (CheckDateNum.daysBetween(todayYear + "-" + (todayMonth < 10 ? "0" + todayMonth : mMonth) + "-"
							+ (todayDay < 10 ? "0" + todayDay : todayDay), sj.getText().toString()) < 1) {
						ToastUtils.show(SaveMoneyActivity.this, "天数少于1，请重新设置");
						return;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				saveMoneySave();
			}
		});
	}

	private void saveMoneySave() {
		// TODO Auto-generated method stub
		SaveMoney saveMoney = new SaveMoney();

		saveMoney.setSavemoney(moneyFloat);
		saveMoney.setSavesr(srMoney);
		saveMoney.setSavetime(sj.getText().toString());
		saveMoney.setUser(user);
		saveMoney.setSpendtotal(0.0);
		saveMoney.save(SaveMoneyActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {

				user.setObjectId(user.getObjectId());
				user.setSaveMoney(true);
				user.update(SaveMoneyActivity.this, new UpdateListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						ToastUtils.show(SaveMoneyActivity.this, "设置成功");
						SaveMoneyActivity.this.finish();
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub

					}
				});

			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub

			}
		});

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

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case 1:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		switch (id) {
		case 1:
			((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
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
			}
		}

	};

}
