package com.meyao.thingmarket.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.trinea.android.common.util.PreferencesUtils;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Account;
import com.meyao.thingmarket.model.User;

public class AccountActivity extends Activity {
	List<Account> accountss;
	ListView list;
	TextView jzc, tj;
	ImageView back;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		user = BmobUser.getCurrentUser(this, User.class);
		jzc = (TextView) findViewById(R.id.jzc);
		list = (ListView) findViewById(R.id.list);
		tj = (TextView) findViewById(R.id.tj);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(AccountActivity.this, AccountAddActivity.class);
				startActivity(mIntent);
			}
		});
		checkUserAccount();
	}

	/**
	 * 批量添加
	 */
	private void addData() {
		// TODO Auto-generated method stub

		List<BmobObject> accounts = new ArrayList<BmobObject>();
		Account account1 = new Account();
		account1.setName("现金");
		account1.setMoney(0.0);
		account1.setUid(PreferencesUtils.getString(this, "username"));
		Account account2 = new Account();
		account2.setName("平安银行卡");
		account2.setMoney(0.0);
		account2.setUid(PreferencesUtils.getString(this, "username"));

		Account account3 = new Account();
		account3.setName("校园卡");
		account3.setMoney(0.0);
		account3.setUid(PreferencesUtils.getString(this, "username"));

		Account account4 = new Account();
		account4.setName("支付宝");
		account4.setMoney(0.0);
		account4.setUid(PreferencesUtils.getString(this, "username"));

		accounts.add(account1);
		accounts.add(account2);
		accounts.add(account3);
		accounts.add(account4);

		new BmobObject().insertBatch(AccountActivity.this, accounts, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				System.out.println("批量添加成功");
				queryObjects();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println("批量添加失败:" + arg1);
			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		queryObjects();
	}

	/**
	 * 查询所有账户
	 */
	private void checkUserAccount() {
		// TODO Auto-generated method stub
		BmobQuery<Account> bmobQuery = new BmobQuery<Account>();
		bmobQuery.addWhereEqualTo("uid", user.getUsername());
		bmobQuery.findObjects(this, new FindListener<Account>() {

			@Override
			public void onSuccess(List<Account> object) {
				if (object.size() == 0) {
					addData();
				}
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * 查询所有账户
	 */
	private void queryObjects() {
		BmobQuery<Account> bmobQuery = new BmobQuery<Account>();
		bmobQuery.addWhereEqualTo("uid", user.getUsername());
		bmobQuery.findObjects(this, new FindListener<Account>() {

			@Override
			public void onSuccess(List<Account> object) {
				if (accountss == null) {
					accountss = new ArrayList<Account>();
				}
				accountss = object;

				float a = 0;
				for (int i = 0; i < accountss.size(); i++) {
					a += accountss.get(i).getMoney();
				}
				AccountAdapter accountAdapter = new AccountAdapter(accountss);

				jzc.setText(a + "");
				list.setAdapter(accountAdapter);

				list.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						if (getIntent().getStringExtra("temp") != null) {
							Intent mIntent = new Intent();
							mIntent.putExtra("accountname", accountss.get(arg2).getName());
							setResult(100, mIntent);
							finish();
						}
					}
				});
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

	class AccountAdapter extends BaseAdapter {
		List<Account> accounts;
		LayoutInflater layoutInflater;

		AccountAdapter(List<Account> accountss) {
			layoutInflater = getLayoutInflater();
			this.accounts = accountss;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return accounts.size();

		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return accounts.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			convertView = layoutInflater.inflate(R.layout.item_account, null);

			((TextView) convertView.findViewById(R.id.ac_name)).setText(accounts.get(arg0).getName());
			((TextView) convertView.findViewById(R.id.q)).setText(accounts.get(arg0).getMoney() + "");

			return convertView;
		}

	}
}
