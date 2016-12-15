package com.meyao.thingmarket.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.baidu.mobstat.StatService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.User;

/**
 * 显示 饼状图
 * 
 * @author Administrator
 * 
 */
public class Chart_PieChart_SRActivity extends Activity {
	private PieChart mChart;

	ImageView imageView;
	ImageView minus, add;
	TextView date;
	int temp = 0;
	public static final String TAG = "ChartActivity";
	int year, month;
	User user;
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achartengine_piechart);
		user = BmobUser.getCurrentUser(this, User.class);

		minus = (ImageView) findViewById(R.id.minus);
		add = (ImageView) findViewById(R.id.add);
		date = (TextView) findViewById(R.id.date);

		mChart = (PieChart) findViewById(R.id.piechart);

		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		Calendar cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH) + 1;

		date.setText(year + "年" + month + "月");
		queryObjects();
		minus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				temp = 0;
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
				temp = 0;
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
		bmobQuery.addWhereEqualTo("temp", "2");
		bmobQuery.addWhereEqualTo("uid", user.getUsername());
		bmobQuery.findObjects(this, new FindListener<Jz_zc>() {

			@Override
			public void onSuccess(List<Jz_zc> object) {
				if (object.size() > 0) {

					PieData mPieData = getPieData(object);
					showChart(mChart, mPieData);
				} else {
					mChart.clear();
				}
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void showChart(PieChart pieChart, PieData pieData) {

		pieChart.setHoleRadius(50f); // 半径
		pieChart.setTransparentCircleRadius(64f); // 半透明圈

		pieChart.setDescription("一账通");

		pieChart.setDrawCenterText(true); // 饼状图中间可以添加文字
		pieChart.setDrawHoleEnabled(true);

		pieChart.setRotationAngle(90); // 初始旋转角度

		pieChart.setRotationEnabled(true); // 可以手动旋转

		if (getIntent().getStringExtra("bbtype").equals("zclx")) {
			pieChart.setCenterText("收入类型"); // 饼状图中间的文字
		}else {
			pieChart.setCenterText("收入账户"); // 饼状图中间的文字
		}

		// 设置数据
		pieChart.setData(pieData);

		Legend mLegend = pieChart.getLegend(); // 设置比例图
		mLegend.setPosition(LegendPosition.RIGHT_OF_CHART); // 最右边显示
		// mLegend.setForm(LegendForm.LINE); //设置比例图的形状，默认是方形
		mLegend.setXEntrySpace(7f);
		mLegend.setYEntrySpace(5f);

		pieChart.animateXY(1000, 1000); // 设置动画
		// mChart.spin(2000, 0, 360);
	}

	private Map<String, Double> addMaps(String key, Double value) {

		Iterator<String> keys = maps.keySet().iterator();
		while (keys.hasNext()) {
			if (key.equals(String.valueOf(keys.next()))) {
				Double temp = maps.get(key);
				// maps.remove(key);
				maps.put(key, temp + value);
				return maps;
			} else {
				continue;
			}
		}
		maps.put(key, value);
		return maps;
	}

	Map<String, Double> maps;

	/**
	 * 数据
	 * 
	 * @param count
	 *            分成几部分
	 * @param range
	 */
	private PieData getPieData(List<Jz_zc> object) {
		ArrayList<String> xValues = new ArrayList<String>(); // xVals用来表示每个饼块上的内容
		ArrayList<Entry> yValues = new ArrayList<Entry>(); // yVals用来表示封装每个饼块的实际数据

		maps = new HashMap<String, Double>();
		
		if (getIntent().getStringExtra("bbtype").equals("zclx")) {
			for (Jz_zc jz_zc : object) {
				addMaps(jz_zc.getType(), jz_zc.getMoney());
			}
		} else {
			for (Jz_zc jz_zc : object) {
				addMaps(jz_zc.getAccount(), jz_zc.getMoney());
			}
		}
//		for (Jz_zc jz_zc : object) {
//			addMaps(jz_zc.getType(), jz_zc.getMoney());
//		}

		// 饼图数据
		int i = 0;

		for (String key : maps.keySet()) {
			xValues.add(key);
			yValues.add(new Entry(maps.get(key).floatValue(), i++));
		}

		// y轴的集合
		PieDataSet pieDataSet;
		if (getIntent().getStringExtra("bbtype").equals("zclx")) {
			pieDataSet = new PieDataSet(yValues, "收入类型"/* 显示在比例图上 */);
			
		}else {
			pieDataSet = new PieDataSet(yValues, "收入账户"/* 显示在比例图上 */);			
		}
		pieDataSet.setSliceSpace(2f); // 设置个饼状图之间的距离

		ArrayList<Integer> colors = new ArrayList<Integer>();

		// 饼图颜色
		colors.add(Color.rgb(114, 188, 223));
		colors.add(Color.rgb(255, 123, 124));
		colors.add(Color.rgb(57, 135, 200));
		colors.add(Color.rgb(205, 205, 205));
		colors.add(Color.rgb(255, 123, 124));

		pieDataSet.setColors(colors);

		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float px = 5 * (metrics.densityDpi / 160f);
		pieDataSet.setSelectionShift(px); // 选中态多出的长度

		PieData pieData = new PieData(xValues, pieDataSet);

		return pieData;
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
