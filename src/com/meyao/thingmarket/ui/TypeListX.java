package com.meyao.thingmarket.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.trinea.android.common.util.PreferencesUtils;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.TypeX;

public class TypeListX extends Activity {
	ImageView back;
	ListView listView;
	TextView tj, title;
	String pid;
	List<String> data = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.type);
		listView = (ListView) findViewById(R.id.list);
		tj = (TextView) findViewById(R.id.tj);
		title = (TextView) findViewById(R.id.title);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		pid = getIntent().getStringExtra("pid");

		title.setText("子类");

		tj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(TypeListX.this, TypeAddActivity.class);
				mIntent.putExtra("tp", "0");
				mIntent.putExtra("pid", pid);
				startActivity(mIntent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		queryObjects();
	}

	private void queryObjects() {
		String[] names = {PreferencesUtils.getString(this, "username"), "q"};
		BmobQuery<TypeX> bmobQuery = new BmobQuery<TypeX>();
		bmobQuery.addWhereEqualTo("pid", pid);
		bmobQuery.addWhereContainedIn("uid", Arrays.asList(names));
		bmobQuery.setLimit(50);
		// 先从缓存取数据，如果没有的话，再从网络取。
		bmobQuery.findObjects(this, new FindListener<TypeX>() {

			@Override
			public void onSuccess(List<TypeX> object) {
				data.clear();
				for (TypeX typeZC : object) {
					data.add(typeZC.getName());
				}
				listView.setAdapter(new ArrayAdapter<String>(TypeListX.this, R.layout.list_text_items, data));
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						if (getIntent().getStringExtra("temp") != null) {
							Intent mIntent = new Intent();
							mIntent.putExtra("typename", data.get(arg2));
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
}
