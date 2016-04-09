package com.meyao.thingmarket.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.meyao.thingmarket.R;

public class CharMainActivity extends Activity {
	ImageView back;
	TextView zclx, zczh, srlx, tjsr, tjzc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chartmain);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		zclx = (TextView) findViewById(R.id.zclx);
		zczh = (TextView) findViewById(R.id.zczh);
		srlx = (TextView) findViewById(R.id.srlx);
		tjsr = (TextView) findViewById(R.id.tjsr);
		tjzc = (TextView) findViewById(R.id.tjzc);

		zclx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(CharMainActivity.this, ChartActivity.class);
				mIntent.putExtra("bbtype", "zclx");
				startActivity(mIntent);
			}
		});
		zczh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(CharMainActivity.this, ChartActivity.class);
				mIntent.putExtra("bbtype", "zczh");
				startActivity(mIntent);
			}
		});
		srlx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(CharMainActivity.this, Chart_zc_zhActivity.class);
				startActivity(mIntent);
			}
		});
		tjsr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(CharMainActivity.this, Chart_qnActivity.class);
				mIntent.putExtra("bbtype", "srlx");
				startActivity(mIntent);
			}
		});
		tjzc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(CharMainActivity.this, Chart_qnActivity.class);
				mIntent.putExtra("bbtype", "zclx");
				startActivity(mIntent);
			}
		});
	}

}
