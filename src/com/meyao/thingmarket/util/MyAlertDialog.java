//package com.meyao.thingmarket.util;
//
//import java.lang.ref.WeakReference;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.meyao.thingmarket.R;
//
//public class MyAlertDialog extends AlertDialog {
//
//	private final static String TAG = "MyAlertDialog";
//
//	protected MyAlertDialog(Context context) {
//		super(context);
//	}
//
//	/**
//	 * @param context
//	 * @param theme
//	 */
//	protected MyAlertDialog(Context context, int theme) {
//		super(context, theme);
//	}
//
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		return super.onKeyDown(keyCode, event);
//	}
//
//	@Override
//	public boolean onKeyUp(int keyCode, KeyEvent event) {
//		return super.onKeyDown(keyCode, event);
//	}
//
//	public static class MBuilder extends AlertDialog.Builder {
//
//		public MBuilder(Context arg0) {
//			super(arg0);
//			// TODO Auto-generated constructor stub
//		}
//
//		@Override
//		public AlertDialog show() {
//			 final MyAlertDialog dialog = create();
//			Log.d(TAG, "show");
//			this.create();
//			dialog.show();
//			installContent();
//			return dialog;
//		}
//
//		private void installContent() {
//			Log.d(TAG, "installContent");
//			mDialogInterface = (DialogInterface) dialog;
//			if (title != null)
//				((TextView) view.findViewById(R.id.title)).setText(this.title);
//			if (title != null)
//				((TextView) view.findViewById(R.id.message)).setText(this.message);
//			if (iconId != -1)
//				((ImageView) view.findViewById(R.id.icon)).setImageResource(iconId);
//			if (view != null)
//				dialog.getWindow().setContentView(view);
//
//			mButtonPositive = (Button) view.findViewById(R.id.button1);
//			mButtonPositive.setOnClickListener(mButtonHandler);
//			mButtonPositive.setText(mButtonPositiveText);
//
//			mButtonNegative = (Button) view.findViewById(R.id.button2);
//			mButtonNegative.setOnClickListener(mButtonHandler);
//			mButtonNegative.setText(mButtonNegativeText);
//		}
//
//		public void setButton(int whichButton, CharSequence text, DialogInterface.OnClickListener listener, Message msg) {
//
//			if (msg == null && listener != null) {
//				msg = mHandler.obtainMessage(whichButton, listener);
//			}
//
//			switch (whichButton) {
//
//			case DialogInterface.BUTTON_POSITIVE:
//				mButtonPositiveText = text;
//				mButtonPositiveMessage = msg;
//				break;
//
//			case DialogInterface.BUTTON_NEGATIVE:
//				mButtonNegativeText = text;
//				mButtonNegativeMessage = msg;
//				break;
//
//			// case DialogInterface.BUTTON_NEUTRAL:
//			// mButtonNeutralText = text;
//			// mButtonNeutralMessage = msg;
//			// break;
//
//			default:
//				throw new IllegalArgumentException("Button does not exist");
//			}
//		}
//
//		View.OnClickListener mButtonHandler = new View.OnClickListener() {
//			public void onClick(View v) {
//				Message m = null;
//				if (v == mButtonPositive && mButtonPositiveMessage != null) {
//					m = Message.obtain(mButtonPositiveMessage);
//				} else if (v == mButtonNegative && mButtonNegativeMessage != null) {
//					m = Message.obtain(mButtonNegativeMessage);
//				}
//				// else if (v == mButtonNeutral && mButtonNeutralMessage !=
//				// null) {
//				// m = Message.obtain(mButtonNeutralMessage);
//				// }
//				if (m != null) {
//					m.sendToTarget();
//				}
//
//				// Post a message so we dismiss after the above handlers are
//				// executed
//				mHandler.obtainMessage(ButtonHandler.MSG_DISMISS_DIALOG, mDialogInterface).sendToTarget();
//			}
//		};
//
//	}
//
//	public static final class ButtonHandler extends Handler {
//		// Button clicks have Message.what as the BUTTON{1,2,3} constant
//		private static final int MSG_DISMISS_DIALOG = 1;
//
//		private WeakReference<DialogInterface> mDialog;
//
//		public ButtonHandler(DialogInterface dialog) {
//			mDialog = new WeakReference<DialogInterface>(dialog);
//		}
//
//		@Override
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//
//			case DialogInterface.BUTTON_POSITIVE:
//			case DialogInterface.BUTTON_NEGATIVE:
//			case DialogInterface.BUTTON_NEUTRAL:
//				((DialogInterface.OnClickListener) msg.obj).onClick(mDialog.get(), msg.what);
//				break;
//
//			case MSG_DISMISS_DIALOG:
//				((DialogInterface) msg.obj).dismiss();
//			}
//		}
//	}
//}