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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.User;
import com.meyao.thingmarket.util.MapKeyComparator;

/**
 * 显示 柱状图 NEW
 * 
 * @author Administrator
 * 
 */
public class Chart_BarChartActivity extends Activity {
	LinearLayout chart_date;

	private BarChart mChart;

	ImageView imageView;
	int temp = 0;
	int year, month;
	User user;
	ImageView back;
	Map<String, Double> maps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achartengine_barchart);
		user = BmobUser.getCurrentUser(this, User.class);

		chart_date = (LinearLayout) findViewById(R.id.chart_date);
		chart_date.setVisibility(View.GONE);

		mChart = (BarChart) findViewById(R.id.barchart);

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
					BarData lineData = getPieData();
					showChart(mChart, lineData);
				}
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void showChart(BarChart barChart, BarData barData) {
		
		barChart.setDrawBorders(false);  ////是否在折线图上添加边框   
        
        barChart.setDescription("");// 数据描述      
          
        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView      
        barChart.setNoDataTextDescription("You need to provide data for the chart.");      
                 
        barChart.setDrawGridBackground(false); // 是否显示表格颜色      
//        barChart.setGridBackgroundColor(Color.WHITE); // 表格的的颜色，在这里是是给颜色设置一个透明度      
        
        barChart.setTouchEnabled(true); // 设置是否可以触摸      
       
        barChart.setDragEnabled(true);// 是否可以拖拽      
        barChart.setScaleEnabled(true);// 是否可以缩放      
      
        barChart.setPinchZoom(false);//       
      
      barChart.setBackgroundColor(Color.TRANSPARENT);// 设置背景      
          
//        barChart.setDrawBarShadow(true);  
         
        barChart.setData(barData); // 设置数据      
  
        Legend mLegend = barChart.getLegend(); // 设置比例图标示  
      
        mLegend.setForm(LegendForm.CIRCLE);// 样式      
        mLegend.setFormSize(6f);// 字体      
        mLegend.setTextColor(Color.BLACK);// 颜色      
          
//      X轴设定  
//      XAxis xAxis = barChart.getXAxis();  
//      xAxis.setPosition(XAxisPosition.BOTTOM);  
      
        barChart.animateX(2500); // 立即执行的动画,x轴  
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
	private BarData getPieData() {
		ArrayList<String> xValues = new ArrayList<String>(); // xVals用来表示每个饼块上的内容
		ArrayList<BarEntry> yValues = new ArrayList<BarEntry>(); // yVals用来表示封装每个饼块的实际数据

		// maps = new HashMap<String, Double>();

		// 饼图数据
		int i = 0;

		for (String key : maps.keySet()) {
			xValues.add(key);
			yValues.add(new BarEntry(maps.get(key).floatValue(), i++));
		}

		// y轴的集合
		// LineDataSet lineDataSet;
		BarDataSet barDataSet;
		if (getIntent().getStringExtra("bbtype").equals("zclx")) {
			barDataSet = new BarDataSet(yValues, "支出类型"/* 显示在比例图上 */);

		} else {
			barDataSet = new BarDataSet(yValues, "支出账户"/* 显示在比例图上 */);
		}

		ArrayList<Integer> colors = new ArrayList<Integer>();

		// 饼图颜色
		colors.add(Color.rgb(114, 188, 223));
		colors.add(Color.rgb(255, 123, 124));
		colors.add(Color.rgb(57, 135, 200));
		colors.add(Color.rgb(205, 205, 205));
		colors.add(Color.rgb(255, 123, 124));
		barDataSet.setColors(colors);

		// lineDataSet.setColors(colors);
		ArrayList<IBarDataSet> barDataSets = new ArrayList<IBarDataSet>();
		barDataSets.add(barDataSet); // add the datasets

		BarData barData = new BarData(xValues, barDataSets);

		return barData;
	}
}
