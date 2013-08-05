package com.dianping;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends Activity{

	private WebView wView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_layout);
		initWebView();
		Intent intent = getIntent();
		if(intent.hasExtra("url"))
			wView.loadUrl(intent.getStringExtra("url"));
	}
	
	private void initWebView(){
		wView = (WebView)this.findViewById(R.id.webview);
		wView.getSettings().setJavaScriptEnabled(true);
		wView.getSettings().setLoadWithOverviewMode(true);
		wView.getSettings().setBuiltInZoomControls(true);
		wView.getSettings().setSupportMultipleWindows(true);
		wView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
			
		});
	}

}
