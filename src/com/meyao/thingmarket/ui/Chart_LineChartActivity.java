package com.meyao.thingmarket.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.User;
import com.meyao.thingmarket.util.MapKeyComparator;

/**
 * 显示 折线图 NEW
 * 
 * @author Administrator
 * 
 */
public class Chart_LineChartActivity extends Activity {
	LinearLayout chart_date;

	private LineChart mChart;

	ImageView imageView;
	int temp = 0;
	int year, month;
	User user;
	ImageView back;
	Map<String, Double> maps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achartengine_linechart);
		user = BmobUser.getCurrentUser(this, User.class);

		chart_date = (LinearLayout) findViewById(R.id.chart_date);
		chart_date.setVisibility(View.GONE);

		mChart = (LineChart) findViewById(R.id.linechart);

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
		// month = cal.get(Calendar.MONTH) + 1;
		month = 1;
		maps = new HashMap<String, Double>();
		for (int i = 1; i < 13; i++) {
			if (month < 13) {
				queryObjects(month);
				month++;
			}
		}

	}

	private void queryObjects(final int month) {
		BmobQuery<Jz_zc> bmobQuery = new BmobQuery<Jz_zc>();
		bmobQuery.addWhereContains("time", year + "-" + (month < 10 ? "0" + month : month));
		if (getIntent().getStringExtra("bbtype").equals("zclx")) {
			bmobQuery.addWhereEqualTo("temp", "1");
		} else {
			bmobQuery.addWhereEqualTo("temp", "2");
		}
		bmobQuery.addWhereEqualTo("uid", user.getUsername());
		bmobQuery.findObjects(this, new FindListener<Jz_zc>() {

			@Override
			public void onSuccess(List<Jz_zc> object) {
				double money = 0;
				for (Jz_zc jz_zc : object) {
					money += jz_zc.getMoney();
				}
				if (month < 10) {
					maps.put("0" + month + "月", money);
				} else {
					maps.put(month + "月", money);
				}
				if (month == 12) {
					maps = sortMapByKey(maps); // 按Key进行排序
					LineData lineData = getPieData();
					showChart(mChart, lineData);
				}
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}
	private void showChart(LineChart lineChart, LineData lineData) {
		lineChart.setDrawBorders(false); // 是否在折线图上添加边框

		// no description text
		lineChart.setDescription("");// 数据描述
		// 如果没有数据的时候，会显示这个，类似listview的emtpyview
		lineChart.setNoDataTextDescription("You need to provide data for the chart.");

		// enable / disable grid background
		lineChart.setDrawGridBackground(false); // 是否显示表格颜色
		lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

		// enable touch gestures
		lineChart.setTouchEnabled(true); // 设置是否可以触摸

		// enable scaling and dragging
		lineChart.setDragEnabled(true);// 是否可以拖拽
		lineChart.setScaleEnabled(true);// 是否可以缩放

		// if disabled, scaling can be done on x- and y-axis separately
		lineChart.setPinchZoom(false);//
//		 lineChart.setBackgroundColor(Color.rgb(137, 230, 81));// 设置背景
		 //???
//		 lineChart.setViewPortOffsets(10, 0, 10, 0);
		// add data
		lineChart.setData(lineData); // 设置数据

		// get the legend (only possible after setting data)
        
		//X轴样式。
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
//        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        //Y轴样式。
        YAxis leftAxis = lineChart.getAxisLeft();
//        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(10, true);
        
		Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的

		// modify the legend ...
		// mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
//		mLegend.setForm(LegendForm.CIRCLE);// 样式
//		mLegend.setFormSize(6f);// 字体
//		mLegend.setTextColor(Color.WHITE);// 颜色
		// mLegend.setTypeface(mTf);// 字体
		mLegend.setForm(LegendForm.LINE);
		mLegend.setEnabled(true);
//		mLegend.setTypeface(tf);
		mLegend.setTextSize(11f);
		mLegend.setTextColor(Color.BLUE);
		mLegend.setPosition(LegendPosition.BELOW_CHART_LEFT);
	        
	        
		lineChart.animateX(2500); // 立即执行的动画,x轴
	}

	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Double> sortMapByKey(Map<String, Double> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, Double> sortMap = new TreeMap<String, Double>(new MapKeyComparator());
		sortMap.putAll(map);
		return sortMap;
	}

	/**
	 * 数据
	 * 
	 * @param count
	 *            分成几部分
	 * @param range
	 */
	private LineData getPieData() {
		ArrayList<String> xValues = new ArrayList<String>(); // xVals用来表示每个饼块上的内容
		ArrayList<Entry> yValues = new ArrayList<Entry>(); // yVals用来表示封装每个饼块的实际数据

		// maps = new HashMap<String, Double>();

		// 饼图数据
		int i = 0;

		for (String key : maps.keySet()) {
			xValues.add(key);
			yValues.add(new Entry(maps.get(key).floatValue(), i++));
		}

		// y轴的集合
		// LineDataSet lineDataSet;
		LineDataSet lineDataSet;
		if (getIntent().getStringExtra("bbtype").equals("zclx")) {
			lineDataSet = new LineDataSet(yValues, "支出类型"/* 显示在比例图上 */);

		} else {
			lineDataSet = new LineDataSet(yValues, "支出账户"/* 显示在比例图上 */);
		}
		lineDataSet.setLineWidth(1.75f); // 线宽
		lineDataSet.setCircleRadius(5f);
		lineDataSet.setColor(Color.parseColor("#8cebff"));// 显示颜色
		lineDataSet.setCircleColor(Color.parseColor("#b7b4b7"));// 圆形的颜色
		lineDataSet.setHighLightColor(Color.parseColor("#ff8e9c")); // 高亮的线的颜色
		lineDataSet.setDrawValues(false);
//		ArrayList<Integer> colors = new ArrayList<Integer>();
//
//		// 饼图颜色
//		colors.add(Color.rgb(114, 188, 223));
//		colors.add(Color.rgb(255, 123, 124));
//		colors.add(Color.rgb(57, 135, 200));
//		colors.add(Color.rgb(205, 205, 205));
//		colors.add(Color.rgb(255, 123, 124));
//
//		lineDataSet.setColors(colors);

		ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
		dataSets.add(lineDataSet); // add the datasets

		// create a data object with the datasets
		LineData data = new LineData(xValues, dataSets);

		return data;
	}
}
