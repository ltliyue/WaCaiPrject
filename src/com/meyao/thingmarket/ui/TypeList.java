package com.meyao.thingmarket.ui;

import java.util.ArrayList;
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
import com.meyao.thingmarket.model.TypeD;

public class TypeList extends Activity {
	ImageView back;
	ListView listView;
	TextView tj, title;
	String type;
	List<String> objectIds = new ArrayList<String>();

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
		type = getIntent().getStringExtra("type");

		if (type.equals("1")) {
			title.setText("支出类型");
		} else {
			title.setText("收入类型");
		}
		tj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(TypeList.this, TypeAddActivity.class);
				mIntent.putExtra("tp", type);
				startActivity(mIntent);
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		queryObjects();
	}

	private void queryObjects() {
		BmobQuery<TypeD> bmobQuery = new BmobQuery<TypeD>();
		bmobQuery.addWhereEqualTo("type", type);
		bmobQuery.addWhereEqualTo("uid", PreferencesUtils.getString(this, "username"));
		bmobQuery.addWhereEqualTo("uid", null);
		bmobQuery.setLimit(50);
		// bmobQuery.addWhereNotEqualTo("age", 25);
		// bmobQuery.addQueryKeys("objectId");
		// bmobQuery.setLimit(10);
		// bmobQuery.setSkip(15);
		// bmobQuery.order("createdAt");
		// bmobQuery.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK); //
		// 先从缓存取数据，如果没有的话，再从网络取。
		bmobQuery.findObjects(this, new FindListener<TypeD>() {

			@Override
			public void onSuccess(List<TypeD> object) {
				List<String> data = new ArrayList<String>();
				for (TypeD typeZC : object) {
					data.add(typeZC.getName());
					objectIds.add(typeZC.getObjectId());
				}
				listView.setAdapter(new ArrayAdapter<String>(TypeList.this,
						R.layout.list_text_items, data));

				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						Intent mIntent = new Intent(TypeList.this, TypeListX.class);
						mIntent.putExtra("pid", objectIds.get(arg2));
						if (getIntent().getStringExtra("temp") != null) {
							mIntent.putExtra("temp", "0");
							startActivityForResult(mIntent, 1);
						} else {
							startActivity(mIntent);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 100) {
			setResult(200, data);
			finish();
		}
	}
}
