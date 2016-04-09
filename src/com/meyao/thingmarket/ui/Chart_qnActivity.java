package com.meyao.thingmarket.ui;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.User;
import com.meyao.thingmarket.util.MapKeyComparator;

/**
 * 显示 饼状图
 * 
 * @author Administrator
 * 
 */
public class Chart_qnActivity extends Activity {
	LinearLayout chart_date;
	ImageView back;
	Map<String, Double> maps;
	int temp = 0;
	private LinearLayout rootView;
	int year, month;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achartengine);
		user = BmobUser.getCurrentUser(this, User.class);
		rootView = (LinearLayout) findViewById(R.id.chart_parent);

		chart_date = (LinearLayout) findViewById(R.id.chart_date);
		chart_date.setVisibility(View.GONE);
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
					showChart();
				}
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
				System.out.println("error");
			}
		});
	}

	void showChart() {
		if (maps.size() == 0) {
			return;
		}
		rootView.removeAllViews();
		// 饼图
		rootView.addView(getLineGraphView(this));

	}

	/**
	 * 折线图
	 * 
	 * @param context
	 * @param data
	 * @return
	 */
	private GraphicalView getLineGraphView(Context context) {
		XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
		XYSeries series = new XYSeries("每月消费状况");
		// for (int i = 0; i < maps.size(); i++) {
		// series.add(i + 1, data.get(i).value);
		// }
		for (Map.Entry<String, Double> entry : maps.entrySet()) {
			series.add(temp + 1, entry.getValue());
			temp++;
		}
		mDataset.addSeries(series);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		mRenderer.setXTitle("\n月份");// 设置为X轴的标题
		mRenderer.setYTitle("金钱（元）");// 设置y轴的标题
		mRenderer.setAxisTitleTextSize(20);// 设置xy轴标题文本大小
		mRenderer.setChartTitleTextSize(20);// 设置图表标题文字的大小
		mRenderer.setLabelsTextSize(15);// 设置标签的文字大小
		mRenderer.setLegendTextSize(20);// 设置图例文本大小
		mRenderer.setPointSize(8f);// 设置点的大小
		mRenderer.setYAxisMin(0);// 设置y轴最小值是0
		double maxValue = getMaxValue();
		mRenderer.setYAxisMax(14 * maxValue / 10);
		mRenderer.setXAxisMin(0);
		mRenderer.setXAxisMax(maps.size() + 1);
		mRenderer.setShowGrid(true);// 显示网格
		mRenderer.setPanEnabled(false, false);// 设置x方向可以滑动，y方向不可以滑动
		mRenderer.setZoomEnabled(false, false);// 设置x，y方向都不可以放大或缩小

		// for (int i = 0; i < data.size(); i++) {
		// mRenderer.addXTextLabel(i + 1, data.get(i).label);
		// }
		temp = 1;
		for (Map.Entry<String, Double> entry : maps.entrySet()) {
			mRenderer.addXTextLabel(temp, entry.getKey());
			temp++;
		}
		mRenderer.setXLabels(0);// 设置只显示如1月，2月等替换后的东西，不显示1,2,3等
		mRenderer.setMargins(new int[] { 20, 50, 70, 20 });// 设置视图位置 上 左 下 右

		// 设置颜色
		mRenderer.setXLabelsColor(Color.BLACK);
		mRenderer.setYLabelsColor(0, Color.BLACK);
		mRenderer.setMarginsColor(Color.parseColor("#f2f2f2"));
		mRenderer.setLabelsColor(Color.BLACK);

		mRenderer.setPanEnabled(false);// 移动
		mRenderer.setZoomEnabled(false);// 缩放
		mRenderer.setFitLegend(false);// 调整合适的位置
		mRenderer.setAntialiasing(true);
		mRenderer.setClickEnabled(false);// 设置是否可以滑动及放大缩小;
		mRenderer.setInScroll(true);

		XYSeriesRenderer r = new XYSeriesRenderer();
		r.setColor(Color.BLUE);// 设置颜色
		r.setPointStyle(PointStyle.CIRCLE);// 设置点的样式
		r.setFillPoints(true);// 填充点（显示的点是空心还是实心）
		r.setDisplayChartValues(true);// 将点的值显示出来
		r.setDisplayChartValuesDistance(maps.size());
		r.setChartValuesSpacing(30);// 显示的点的值与图的距离
		r.setChartValuesTextSize(20);// 点的值的文字大小
		r.setLineWidth(2);// 设置线宽
		mRenderer.addSeriesRenderer(r);

		final GraphicalView view = ChartFactory.getLineChartView(context, mDataset, mRenderer);
		return view;
	}

	private double getMaxValue() {
		double max = 0;
		for (Map.Entry<String, Double> entry : maps.entrySet()) {
			if (entry.getValue() > max) {
				max = entry.getValue();
			}
		}
		return max;
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

}
