package org.softwaregeeks.needletagger;

import org.softwaregeeks.needletagger.common.ActivityHelper;
import org.softwaregeeks.needletagger.common.LogManager;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReportDialog extends Activity {

	private static final int MESSAGE_GET_SEND = 201;

	private OnClickListener onClickListener;

	private EditText messageEditText;
	private Button sendButton;
	private Button cancelButton;

	private Thread processThread;
	private Handler processHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.report);

		setInit();
		setHandler();
	}

	private void setInit() {
		setOnClickListener();
		setComponent();
	}

	public void setComponent() {

		messageEditText = (EditText) findViewById(R.id.message);
		sendButton = (Button) findViewById(R.id.send);
		sendButton.setOnClickListener(onClickListener);

		cancelButton = (Button) findViewById(R.id.cancel);
		cancelButton.setOnClickListener(onClickListener);
	}

	private void setHandler() {
		processHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				if (msg != null) {
					switch (msg.what) {
					case MESSAGE_GET_SEND: {
						Boolean isProcessed = (Boolean) msg.obj;
						if(isProcessed)
						{
							Toast.makeText(ReportDialog.this, getString(R.string.reportResultCaption), Toast.LENGTH_LONG).show();
							ActivityHelper.setEndProcess(ReportDialog.this);
							finish();
						}
						else
						{
							Toast.makeText(ReportDialog.this, getString(R.string.reportResultFailCaption), Toast.LENGTH_LONG).show();
							ActivityHelper.setEndProcess(ReportDialog.this);
						}
					}
						break;
					}
				}
			}
		};
	}

	public void setOnClickListener() {
		onClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.send: {
						sendMessage();
					}
					break;
					case R.id.cancel: {
						finish();
					}
				}
			}
		};
	}

	public void sendMessage() {
		
		String message = messageEditText.getText().toString();
		if (message == null || "".equals(message)) {
			Toast.makeText(ReportDialog.this, getString(R.string.reportNoMessageCaption), Toast.LENGTH_LONG).show();
			return;
		}
		
		ActivityHelper.setStartProcess(this);
		processThread = new Thread(new Runnable() {
			@Override
			public void run() {
				String message = messageEditText.getText().toString();
				boolean isProcessed = LogManager.sendLogMessage("",message);
				Message postMessage = Message.obtain(processHandler, MESSAGE_GET_SEND, isProcessed);
				processHandler.sendMessage(postMessage);
			}
		});
		processThread.start();
	}
}