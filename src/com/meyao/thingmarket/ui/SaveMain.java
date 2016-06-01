package com.meyao.thingmarket.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

import com.baidu.mobstat.StatService;
import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.SaveMoney;
import com.meyao.thingmarket.model.User;

public class SaveMain extends Activity {
	ImageView back;
	TextView mbsj, savemoney, sr, huafei;
	User user;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				SaveMoney saveMoney = (SaveMoney) msg.obj;
				mbsj.setText(saveMoney.getSavetime());
				savemoney.setText(saveMoney.getSavemoney() + "");
				sr.setText(saveMoney.getSavesr() + "");
				huafei.setText(saveMoney.getSpendtotal() + "");
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.savemain);

		user = BmobUser.getCurrentUser(this, User.class);
		// TextView cancle = (TextView) findViewById(R.id.cancle);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		mbsj = (TextView) findViewById(R.id.mbsj);
		savemoney = (TextView) findViewById(R.id.savemoney);
		sr = (TextView) findViewById(R.id.sr);
		huafei = (TextView) findViewById(R.id.huafei);

		querySaveMoney();
		// cancle.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// user.setSaveMoney(false);
		// user.update(SaveMain.this, new UpdateListener() {
		//
		// @Override
		// public void onSuccess() {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void onFailure(int arg0, String arg1) {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		// }
		// });
	}

	private void querySaveMoney() {
		// TODO Auto-generated method stub
		BmobQuery<SaveMoney> bmobQuery = new BmobQuery<SaveMoney>();
		bmobQuery.addWhereEqualTo("user", user);
		bmobQuery.addWhereEqualTo("isfinish", false);
		bmobQuery.findObjects(this, new FindListener<SaveMoney>() {

			@Override
			public void onSuccess(List<SaveMoney> arg0) {
				// TODO Auto-generated method stub
				Message message = new Message();
				message.obj = arg0.get(0);
				message.what = 1;
				mHandler.sendMessage(message);
			}

			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub

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
