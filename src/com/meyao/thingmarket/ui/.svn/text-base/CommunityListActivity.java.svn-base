package com.meyao.thingmarket.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.trinea.android.common.util.ToastUtils;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Community;
import com.meyao.thingmarket.model.User;

public class CommunityListActivity extends Activity {
	ImageView back;
	TextView tj, zan;
	ListView listView;
	User user;
	List<Community> weibos = new ArrayList<Community>();
	MyAdapter adapter;
	Community Community;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community);
		user = BmobUser.getCurrentUser(this, User.class);
		Community = new Community();

		tj = (TextView) findViewById(R.id.tj);
		zan = (TextView) findViewById(R.id.zan);
		back = (ImageView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tj.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent mIntent = new Intent(CommunityListActivity.this, CommunityAddListActivity.class);
				startActivity(mIntent);
			}
		});
		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		findWeibos();
	}

	/**
	 * 查询微博
	 */
	private void findWeibos() {
		BmobQuery<Community> query = new BmobQuery<Community>();
		query.order("-createdAt");
		// query.addWhereEqualTo("author", user); // 查询当前用户的所有微博
		query.include("author"); // 希望在查询微博信息的同时也把发布人的信息查询出来，可以使用include方法
		query.findObjects(this, new FindListener<Community>() {
			@Override
			public void onSuccess(List<Community> object) {
				// TODO Auto-generated method stub
				weibos = object;
				adapter = new MyAdapter(CommunityListActivity.this, weibos, user);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();

			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

	private static class MyAdapter extends BaseAdapter {
		List<Community> communities;
		private LayoutInflater mInflater;
		private Context mContext;
		User user;

		public MyAdapter(Context context, List<Community> communities, User user) {
			this.communities = communities;
			mContext = context;
			mInflater = LayoutInflater.from(context);
			this.user = user;
		}

		static class ViewHolder {
			TextView tv_content;
			TextView tv_author;
			TextView zan;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			// return bmobObjects.size();
			return communities.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_weibo, null);

				holder = new ViewHolder();
				holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
				holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);
				holder.zan = (TextView) convertView.findViewById(R.id.zan);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			// Bind the data efficiently with the holder.
			final Community weibo = communities.get(position);

			holder.zan.setText(weibo.getLove() + "");
			holder.zan.setTextColor(Color.parseColor("#000000"));

			holder.tv_author.setText("用户：" + weibo.getAuthor().getUsername());
			holder.tv_content.setText(weibo.getContent());

			holder.zan.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (weibo.getLoveUser() != null) {
						for (int i = 0; i < weibo.getLoveUser().size(); i++) {
							System.out.println(weibo.getLoveUser().get(i));
							if (weibo.getLoveUser().get(i).equals(user.getUsername())) {
								ToastUtils.show(mContext, "您已经赞过啦");
								return;
							}
						}
					}
					weibo.setLove(weibo.getLove() + 1);
					holder.zan.setText(weibo.getLove() + "");
					weibo.increment("love", 1);
					ArrayList<String> loveuserArrayList = new ArrayList<String>();
					loveuserArrayList.add(user.getUsername());
					weibo.setLoveUser(loveuserArrayList);
					weibo.update(mContext, new UpdateListener() {

						@Override
						public void onSuccess() {
							// TODO Auto-generated method stub

							ToastUtils.show(mContext, "点赞成功~");
							// ((CommunityListActivity) mContext).findWeibos();
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							// TODO Auto-generated method stub
						}
					});
				}
			});
			convertView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, CommentListActivity.class);
					intent.putExtra("objectId", weibo.getObjectId());
					intent.putExtra("object", communities.get(position));
					mContext.startActivity(intent);
				}
			});

			return convertView;
		}
	}

}
