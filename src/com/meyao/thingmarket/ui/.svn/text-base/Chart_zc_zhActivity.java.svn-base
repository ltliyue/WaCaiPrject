package com.meyao.thingmarket.ui;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Jz_zc;
import com.meyao.thingmarket.model.User;

/**
 * 显示 饼状图
 * 
 * @author Administrator
 * 
 */
public class Chart_zc_zhActivity extends Activity {
	ImageView imageView;
	LinearLayout chart_date;
	ImageView minus, add;
	TextView date;
	String[] colors = new String[] { "#AFD8F8", "#8BBA00", "#FF8E46", "#04FE7C", "#8BBA00", "#FF8E46", "#04FE7C",
			"#8BBA00", "#FF8E46", "#04FE7C", "#8BBA00", "#FF8E46", "#04FE7C", };
	Map<String, Double> maps;
	int temp = 0;
	public static final String TAG = "ChartActivity";
	private LinearLayout rootView;
	int year, month;
	User user;
	ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.achartengine);
		user = BmobUser.getCurrentUser(this, User.class);
		rootView = (LinearLayout) findViewById(R.id.chart_parent);

		minus = (ImageView) findViewById(R.id.minus);
		add = (ImageView) findViewById(R.id.add);
		date = (TextView) findViewById(R.id.date);

		chart_date = (LinearLayout) findViewById(R.id.chart_date);

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

					maps = new HashMap<String, Double>();
					for (Jz_zc jz_zc : object) {
						addMaps(jz_zc.getType(), jz_zc.getMoney());
					}
					showChart();
				} else {
					rootView.removeAllViews();
					if (imageView == null) {
						imageView = new ImageView(Chart_zc_zhActivity.this);
					}
					imageView.setImageResource(R.drawable.bbbg);
					rootView.addView(imageView);
				}
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

	private Map<String, Double> addMaps(String key, Double value) {

		Iterator keys = maps.keySet().iterator();
		while (keys.hasNext()) {
			if (key.equals(String.valueOf(keys.next()))) {
				Double temp = maps.get(key);
				maps.remove(key);
				maps.put(key, temp + value);
				return maps;
			} else {
				continue;
			}
		}
		;
		maps.put(key, value);
		return maps;

	}

	void showChart() {
		if (maps.size() == 0) {
			return;
		}
		rootView.removeAllViews();
		// 饼图
		rootView.addView(getPieView(this));

	}

	/**
	 * 饼图
	 * 
	 * @param context
	 * @param list
	 * @return
	 */
	private GraphicalView getPieView(Context context) {

		final CategorySeries series = new CategorySeries("pie");
		final DefaultRenderer renderer = new DefaultRenderer();

		for (Map.Entry<String, Double> entry : maps.entrySet()) {
			series.add(entry.getKey(), entry.getValue());
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(Color.parseColor(colors[temp]));
			r.setDisplayBoundingPoints(true);
			// r.setDisplayChartValuesDistance(50);
			r.setDisplayChartValues(true);
			// r.setChartValuesTextSize(60);
			renderer.addSeriesRenderer(r);
			temp++;
		}

		renderer.setLabelsColor(Color.BLACK);
		renderer.setShowLabels(true);
		renderer.setLabelsTextSize(18);
		renderer.setLegendTextSize(26);

		renderer.setDisplayValues(true);// 设置是否在柱体上方显示值
		renderer.setPanEnabled(false);// 移动
		renderer.setZoomEnabled(false);// 缩放
		renderer.setFitLegend(false);// 调整合适的位置
		renderer.setAntialiasing(true);// 设置是否可以滑动及放大缩小;
		renderer.setClickEnabled(false);
		renderer.setInScroll(true);
		renderer.setShowGrid(true);// 显示网格

		GraphicalView view = ChartFactory.getPieChartView(context, series, renderer);
		return view;
	}
}
