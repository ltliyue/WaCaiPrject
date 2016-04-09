package com.meyao.thingmarket.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.trinea.android.common.util.ToastUtils;

import com.meyao.thingmarket.R;
import com.meyao.thingmarket.model.Comment;
import com.meyao.thingmarket.model.Community;
import com.meyao.thingmarket.model.User;

/**
 * 评论详情
 * 
 * @author MaryLee
 * 
 */
public class CommentListActivity extends Activity {
	ImageView back;
	TextView zan, pinglun;
	LinearLayout lin_zan;
	ListView listView;
	EditText et_content;
	Button btn_publish;
	User user;
	static List<Comment> comments = new ArrayList<Comment>();
	MyAdapter adapter;
	Community community = new Community();
	// Community community
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				pinglun.setText(comments.size() + "");
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		user = BmobUser.getCurrentUser(this, User.class);

		// Community.setObjectId(getIntent().getStringExtra("objectId"));
		community = (Community) getIntent().getSerializableExtra("object");

		adapter = new MyAdapter(this);
		back = (ImageView) findViewById(R.id.back);
		et_content = (EditText) findViewById(R.id.et_content);
		btn_publish = (Button) findViewById(R.id.btn_publish);

		listView = (ListView) findViewById(R.id.listview);
		listView.setAdapter(adapter);

		TextView tv_content = (TextView) findViewById(R.id.tv_content);
		TextView tv_author = (TextView) findViewById(R.id.tv_author);
		zan = (TextView) findViewById(R.id.zan);
		lin_zan = (LinearLayout) findViewById(R.id.lin_zan);
		pinglun = (TextView) findViewById(R.id.pinglun2);

		tv_content.setText(community.getContent());
		tv_author.setText("" + community.getAuthor().getUsername());
		zan.setText(community.getLove() + "");
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		lin_zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (community.getLoveUser() != null) {
					for (int i = 0; i < community.getLoveUser().size(); i++) {
						System.out.println(community.getLoveUser().get(i));
						if (community.getLoveUser().get(i).equals(user.getUsername())) {
							ToastUtils.show(CommentListActivity.this, "您已经赞过啦");
							return;
						}
					}
				}
				community.setLove(community.getLove() + 1);
				zan.setText(community.getLove() + "");
				community.increment("love", 1);
				ArrayList<String> loveuserArrayList = new ArrayList<String>();
				loveuserArrayList.add(user.getUsername());
				community.setLoveUser(loveuserArrayList);
				community.update(CommentListActivity.this, new UpdateListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						ToastUtils.show(CommentListActivity.this, "点赞成功~");
						// ((CommunityListActivity) mContext).findWeibos();
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
					}
				});
			}
		});
		btn_publish.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				publishComment(et_content.getText().toString());
			}
		});

		findComments();
	}

	private void findComments() {

		BmobQuery<Comment> query = new BmobQuery<Comment>();
		// 查询这个微博的所有评论,注意：这里的第一个参数是Weibo表中的comment字段
		query.addWhereRelatedTo("comment", new BmobPointer(community));
		query.include("author");
		query.findObjects(this, new FindListener<Comment>() {

			@Override
			public void onSuccess(List<Comment> object) {
				// TODO Auto-generated method stub
				comments = object;
				mHandler.sendEmptyMessage(1);
				adapter.notifyDataSetChanged();
				et_content.setText("");
			}

			@Override
			public void onError(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void publishComment(String content) {
		final Comment comment = new Comment();
		comment.setContent(content);
		comment.setAuthor(user);
		comment.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// TODO Auto-generated method stub
				findComments();
				et_content.setText("");
				ToastUtils.show(CommentListActivity.this, "评论成功");
				// 评论添加成功后将该条评论与weibo关联
				BmobRelation relation = new BmobRelation();
				relation.add(comment);
				community.setComment(relation);
				community.update(CommentListActivity.this, new UpdateListener() {

					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						findComments();
						pinglun.setText(Integer.parseInt(pinglun.getText().toString()) + 1 + "");
					}

					@Override
					public void onFailure(int code, String msg) {
						// TODO Auto-generated method stub
					}
				});
			}

			@Override
			public void onFailure(int code, String msg) {
				// TODO Auto-generated method stub
			}
		});
	}

	private static class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			mInflater = LayoutInflater.from(context);
		}

		static class ViewHolder {
			TextView tv_content;
			TextView tv_author;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return comments.size();
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
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_comment, null);

				holder = new ViewHolder();
				holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
				holder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final Comment comment = comments.get(position);

			if (comment.getAuthor() != null) {
				holder.tv_author.setText("" + comment.getAuthor().getUsername());
			}

			final String str = comment.getContent();

			holder.tv_content.setText(str);

			return convertView;
		}
	}

}
