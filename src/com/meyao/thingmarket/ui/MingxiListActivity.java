package com.meyao.thingmarket.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.User;

public class MingxiListActivity extends Activity {
	ImageView back;
	ImageView minus, add;
	TextView date, tj;
	List<Jz_zc> jz_zcs;
	ListView list;
	int year, month;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mingxi);
		user = BmobUser.getCurrentUser(this, User.class);
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		list = (ListView) findViewById(R.id.list);
		minus = (ImageView) findViewById(R.id.minus);
		add = (ImageView) findViewById(R.id.add);
		date = (TextView) findViewById(R.id.date);

		tj = (TextView) findViewById(R.id.tj);
		tj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(MingxiListActivity.this, CharMainActivity.class);
				startActivity(mIntent);
			}
		});
		date.setText(year + "年" + month + "月");
		queryObjects();
		minus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (month == 1) {
					month = 12;
					year--;
				} else {
					month--;
				}
				date.setText(year + "年" + month + "月");
				queryObjects();
			}
		});
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (month == 12) {
					month = 1;
					year++;
				} else {
					month++;
				}
				date.setText(year + "年" + month + "月");
				queryObjects();
			}
		});
	}

	private void queryObjects() {
		BmobQuery<Jz_zc> bmobQuery = new BmobQuery<Jz_zc>();
		bmobQuery.addWhereContains("time", year + "-" + (month < 10 ? "0" + month : month));
		bmobQuery.addWhereEqualTo("uid", user.getUsername());
		// bmobQuery.addWhereNotEqualTo("age", 25);
		// bmobQuery.addQueryKeys("objectId");
		// bmobQuery.setLimit(10);
		// bmobQuery.setSkip(15);
		// bmobQuery.order("createdAt");
		// bmobQuery.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK); //
		// 先从缓存取数据，如果没有的话，再从网络取。
		bmobQuery.findObjects(this, new FindListener<Jz_zc>() {

			@Override
			public void onSuccess(List<Jz_zc> object) {
				if (jz_zcs == null) {
					jz_zcs = new ArrayList<Jz_zc>();
				}
				jz_zcs = object;

				float a = 0;
				for (int i = 0; i < jz_zcs.size(); i++) {
					a += jz_zcs.get(i).getMoney();
				}
				MingxiAdapter accountAdapter = new MingxiAdapter(jz_zcs);

				list.setAdapter(accountAdapter);

				// list.setOnItemClickListener(new OnItemClickListener() {
				//
				// @Override
				// public void onItemClick(AdapterView<?> arg0, View arg1, int
				// arg2, long arg3) {
				// // TODO Auto-generated method stub
				// if (getIntent().getStringExtra("temp") != null) {
				// Intent mIntent = new Intent();
				// mIntent.putExtra("accountname", jz_zcs.get(arg2).getName());
				// setResult(100, mIntent);
				// finish();
				// }
				// }
				// });
				// list.setOnLongClickListener(new OnLongClickListener() {
				//
				// @Override
				// public boolean onLongClick(View arg0) {
				// // TODO Auto-generated method stub
				// AlertDialog.Builder builder = new
				// AlertDialog.Builder(AccountActivity.this);
				// builder.setTitle("是否删除该账户？");
				// builder.setPositiveButton("确定", new
				// DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog, int whichButton)
				// {
				//
				// }
				// });
				// builder.setNegativeButton("取消", new
				// DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog, int whichButton)
				// {
				// // 这里添加点击确定后的逻辑
				// }
				// });
				// builder.create().show();
				// return true;
				// }
				// });
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

	class MingxiAdapter extends BaseAdapter {
		List<Jz_zc> jzList;
		LayoutInflater layoutInflater;

		MingxiAdapter(List<Jz_zc> jzList) {
			layoutInflater = getLayoutInflater();
			this.jzList = jzList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return jzList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return jzList.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			convertView = layoutInflater.inflate(R.layout.item_mingxi, null);
			if (jzList.get(arg0).getTemp().equals("3")) {
				((TextView) convertView.findViewById(R.id.name)).setText(jzList.get(arg0).getAccount() + " 转 "
						+ jzList.get(arg0).getAccount2());
			} else {
				((TextView) convertView.findViewById(R.id.name)).setText(jzList.get(arg0).getAccount());
			}
			TextView money = (TextView) convertView.findViewById(R.id.q);
			if (jzList.get(arg0).getTemp().equals("1")) {
				((TextView) convertView.findViewById(R.id.time)).setText(jzList.get(arg0).getTime() + " "
						+ jzList.get(arg0).getType());
			} else {
				((TextView) convertView.findViewById(R.id.time)).setText(jzList.get(arg0).getTime());

			}
			if (jzList.get(arg0).getTemp().equals("2")) {
				money.setTextColor(Color.parseColor("#50c38c"));
			}
			money.setText("￥" + jzList.get(arg0).getMoney() + "");
			((TextView) convertView.findViewById(R.id.account)).setText(jzList.get(arg0).getBz());

			return convertView;
		}

	}
}
