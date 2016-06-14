package com.meyao.thingmarket.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.baidu.mobstat.StatService;
import com.meyao.thingmarket.BaseActivty;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Account;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.User;

public class MingxiListActivity extends BaseActivty {
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

	int clickItemPosition;
	MingxiAdapter accountAdapter;

	private void queryObjects() {
		BmobQuery<Jz_zc> bmobQuery = new BmobQuery<Jz_zc>();
		bmobQuery.addWhereContains("time", year + "-" + (month < 10 ? "0" + month : month));
		bmobQuery.addWhereEqualTo("uid", user.getUsername());
		// 先从缓存取数据，如果没有的话，再从网络取。
		bmobQuery.findObjects(this, new FindListener<Jz_zc>() {

			@Override
			public void onSuccess(List<Jz_zc> object) {
				if (jz_zcs == null) {
					jz_zcs = new ArrayList<Jz_zc>();
				}
				jz_zcs = object;

//				float a = 0;
//				for (int i = 0; i < jz_zcs.size(); i++) {
//					a += jz_zcs.get(i).getMoney();
//				}
				accountAdapter = new MingxiAdapter(ct, jz_zcs);

				list.setAdapter(accountAdapter);
				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						
//					}
//				});
//				list.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//					@Override
//					public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
						clickItemPosition = position;
						final AlertDialog dialog = new AlertDialog.Builder(ct).create();
						dialog.setCanceledOnTouchOutside(false);
						dialog.show();
						Window window = dialog.getWindow();
						window.setContentView(R.layout.alertdialog);
						TextView tv_title = (TextView) window.findViewById(R.id.name);
						tv_title.setText("是否删除该记录？");
						Button button1 = (Button) window.findViewById(R.id.btn1);
						Button button2 = (Button) window.findViewById(R.id.btn2);
						button1.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								dialog.dismiss();

							}
						});
						button2.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								removeList(jz_zcs.get(clickItemPosition));
								showLonding();
								
							}
						});
//						return false;
					}

				});
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * 删除
	 * 
	 * @param objectId
	 */
	private void removeList(final Jz_zc jz_zc) {
		final double money = jz_zc.getMoney();

		BmobQuery<Account> bmobQuery = new BmobQuery<Account>();
		bmobQuery.addWhereEqualTo("name", jz_zc.getAccount());
		bmobQuery.addWhereEqualTo("uid", jz_zc.getUid());
		bmobQuery.findObjects(ct, new FindListener<Account>() {

			@Override
			public void onSuccess(List<Account> arg0) {
				Account account = arg0.get(0);
				if (jz_zc.getTemp().equals("1")) {
					account.setMoney(account.getMoney() + money);
				} else if (jz_zc.getTemp().equals("2")) {
					account.setMoney(account.getMoney() - money);
				} else {
					showToast("抱歉，无法删除该条数据");
					return;
				}
				jz_zcs.remove(clickItemPosition);
				account.update(ct, new UpdateListener() {

					@Override
					public void onSuccess() {
						jz_zc.delete(ct, new DeleteListener() {

							@Override
							public void onSuccess() {
								missLonding();
								accountAdapter.notifyDataSetChanged();
								showToast("删除成功~");
							}

							@Override
							public void onFailure(int arg0, String arg1) {
								showToast(arg1);
							}
						});
					}

					@Override
					public void onFailure(int arg0, String arg1) {
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

	class MingxiAdapter extends BaseAdapter {
		List<Jz_zc> jzList;
		public Context context;

		MingxiAdapter(Context context, List<Jz_zc> jzList) {
			this.context = context;
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

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.item_mingxi, null);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				holder.money = (TextView) convertView.findViewById(R.id.q);
				holder.account = (TextView) convertView.findViewById(R.id.account);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (jzList.get(arg0).getTemp().equals("3")) {
				holder.name.setText(jzList.get(arg0).getAccount() + " 转 " + jzList.get(arg0).getAccount2());
			} else {
				holder.name.setText(jzList.get(arg0).getAccount());
			}
			if (jzList.get(arg0).getTemp().equals("1")) {
				holder.time.setText(jzList.get(arg0).getTime() + " " + jzList.get(arg0).getType());
			} else {
				holder.time.setText(jzList.get(arg0).getTime());

			}
			if (jzList.get(arg0).getTemp().equals("2")) {
				holder.money.setTextColor(Color.parseColor("#50c38c"));
			}
			holder.money.setText("￥" + jzList.get(arg0).getMoney() + "");
			holder.account.setText(jzList.get(arg0).getBz());

			return convertView;
		}

		class ViewHolder {
			public TextView name;
			public TextView time;
			public TextView money;
			public TextView account;

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
