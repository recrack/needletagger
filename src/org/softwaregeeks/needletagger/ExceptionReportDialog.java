package org.softwaregeeks.needletagger;

import org.softwaregeeks.needletagger.common.LogManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class ExceptionReportDialog extends Activity {
	private static final int MESSAGE_GET_SEND = 201;

	private Thread processThread;
	private Handler processHandler;

	private String stackTrace;
	private ProgressDialog progressDialog;
	private Activity activity;
	private Context context;
	
	public ExceptionReportDialog(Context context, String stackTrace) {
		this.stackTrace = stackTrace;
		this.context = context;
		createLoginDialog();
		setHandler();
		sendMessage();
	}
	
	public ExceptionReportDialog(Activity activity, String stackTrace) {
		this.activity = activity;
		this.stackTrace = stackTrace;
		this.context = (Context)activity;
		createLoginDialog();
		setHandler();
		sendMessage();
	}

	public void createLoginDialog() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(activity.getString(R.string.exceptionTitleCaption));
		progressDialog.setMessage(activity.getString(R.string.exceptionCaption));
		progressDialog.show();
	}

	private void setHandler() {
		processHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg != null) {
					progressDialog.hide();
				}
			}
		};
	}

	public void sendMessage() {

		processThread = new Thread(new Runnable() {
			@Override
			public void run() {
				boolean isProcessed = LogManager.sendLogMessage(stackTrace, "");
				Message postMessage = Message.obtain(processHandler, MESSAGE_GET_SEND, isProcessed);
				processHandler.sendMessage(postMessage);
			}
		});
		processThread.start();
	}
}