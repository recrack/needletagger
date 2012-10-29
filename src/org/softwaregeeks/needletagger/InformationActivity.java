package org.softwaregeeks.needletagger;

import org.softwaregeeks.needletagger.common.ActivityHelper;
import org.softwaregeeks.needletagger.common.ConfigurationManager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class InformationActivity extends Activity {
	
	private static final String url = "http://needletagger.appspot.com/mobile.jsp";
	private WebView webview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.information);

		setInit();
		load();
	}

	private void setInit() {
		ConfigurationManager.load(this);
		ActivityHelper.setNavigationBar(this);
		webview = (WebView) findViewById(R.id.webview);
	}

	private void load() {
		try {
			webview.getSettings().setJavaScriptEnabled(true);
			webview.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {
					if (progress == 100)
						ActivityHelper.setHeaderProgressBar(InformationActivity.this, false);
				}
			});
			webview.setWebViewClient(new WebViewClient() {
				public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
					Toast.makeText(InformationActivity.this, description, Toast.LENGTH_SHORT).show();
				}
			});
			webview.loadUrl(url);
		} catch (Exception ex) {
			new ExceptionReportDialog(this, ex.toString());
		}
	}
}