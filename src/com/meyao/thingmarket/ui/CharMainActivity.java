package com.meyao.thingmarket.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.meyao.thingmarket.R;

public class CharMainActivity extends Activity {
	ImageView back;
	TextView zclx, zczh, srlx, srzh, tjsr, tjzc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		srzh = (TextView) findViewById(R.id.srzh);
		tjsr = (TextView) findViewById(R.id.tjsr);
		tjzc = (TextView) findViewById(R.id.tjzc);

		zclx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(CharMainActivity.this, Chart_PieChartActivity.class);
				mIntent.putExtra("bbtype", "zclx");
				startActivity(mIntent);
			}
		});
		zczh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(CharMainActivity.this, Chart_PieChartActivity.class);
				mIntent.putExtra("bbtype", "zczh");
				startActivity(mIntent);
			}
		});
		srlx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(CharMainActivity.this, Chart_PieChart_SRActivity.class);
				mIntent.putExtra("bbtype", "zclx");
				startActivity(mIntent);
			}
		});
		srzh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent mIntent = new Intent(CharMainActivity.this, Chart_PieChart_SRActivity.class);
				mIntent.putExtra("bbtype", "zczh");
				startActivity(mIntent);
			}
		});
		tjsr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(CharMainActivity.this, Chart_qnActivity.class);
				// Intent mIntent = new Intent(CharMainActivity.this,
				// Chart_LineChartActivity.class);
				mIntent.putExtra("bbtype", "srlx");
				startActivity(mIntent);
			}
		});
		tjzc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(CharMainActivity.this, Chart_qnActivity.class);
				// Intent mIntent = new Intent(CharMainActivity.this,
				// Chart_BarChartActivity.class);
				mIntent.putExtra("bbtype", "zclx");
				startActivity(mIntent);
			}
		});
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
