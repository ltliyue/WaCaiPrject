package com.meyao.thingmarket.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import cn.trinea.android.common.util.ToastUtils;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Community;
import com.meyao.thingmarket.model.User;

public class CommunityAddListActivity extends Activity {
	ImageView back, wc;
	EditText content;
	User user;

	static List<Community> weibos = new ArrayList<Community>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_add);
		user = BmobUser.getCurrentUser(this, User.class);

		content = (EditText) findViewById(R.id.content);
		wc = (ImageView) findViewById(R.id.wc);
		wc.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (content.length() > 200) {
					ToastUtils.show(CommunityAddListActivity.this, "您已超过200字符");
					return;
				}
				publishWeibo(content.getText().toString());
			}
		});
	}

	/**
	 * 发布微博，发表微博时关联了用户类型，是一对一的体现
	 */
	private void publishWeibo(String content) {

		// 创建微博信息
		Community weibo = new Community();
		weibo.setContent(content);
		weibo.setAuthor(user);
		weibo.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				ToastUtils.show(CommunityAddListActivity.this, "发布成功");
				finish();
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

}
