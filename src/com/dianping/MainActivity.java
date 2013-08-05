package com.dianping;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity implements OnClickListener {

	private TextView home;
	private TextView radar;
	private TextView concertraition;
	private TextView dynomic;
	private TextView more;
	
	private TabHost tabHost;
	
	private Intent homeIntent;
	private Intent radarIntent;
	private Intent selectorIntent;
	private Intent dynomicIntent;
	private Intent moreIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		tabHost = this.getTabHost();
		initView();

		initTabs();
	}

	void initView() {

		home = (TextView) this.findViewById(R.id.home);
		radar = (TextView) this.findViewById(R.id.radar);
		concertraition = (TextView) this.findViewById(R.id.concertraition);
		dynomic = (TextView) this.findViewById(R.id.dynomic);
		more = (TextView) this.findViewById(R.id.more);
		
		home.setOnClickListener(this);
		radar.setOnClickListener(this);
		concertraition.setOnClickListener(this);
		dynomic.setOnClickListener(this);
		more.setOnClickListener(this);

		home.setSelected(true);
	}
	
	void initTabs(){
		homeIntent = new Intent(this,HomeActivity.class);
		radarIntent = new Intent(this,RaderActivity.class);
		selectorIntent = new Intent(this,SelectorActivity.class);
		dynomicIntent = new Intent(this,DynomicActivity.class);
		moreIntent = new Intent(this,MoreActivity.class);
		
		tabHost.addTab(tabHost.newTabSpec("home").setContent(homeIntent).setIndicator("a"));
		tabHost.addTab(tabHost.newTabSpec("rader").setContent(radarIntent).setIndicator("b"));
		tabHost.addTab(tabHost.newTabSpec("selector").setContent(selectorIntent).setIndicator("c"));
		tabHost.addTab(tabHost.newTabSpec("dynomic").setContent(dynomicIntent).setIndicator("d"));
		tabHost.addTab(tabHost.newTabSpec("more").setContent(moreIntent).setIndicator("e"));
	}

	@Override
	public void onClick(View v) {
		initButtons();
		switch (v.getId()) {
		case R.id.home:
			home.setSelected(true);
			tabHost.setCurrentTabByTag("home");
			break;
		case R.id.radar:
			radar.setSelected(true);
			tabHost.setCurrentTabByTag("rader");
			break;
		case R.id.concertraition:
			concertraition.setSelected(true);
			tabHost.setCurrentTabByTag("selector");
			break;
		case R.id.dynomic:
			dynomic.setSelected(true);
			tabHost.setCurrentTabByTag("dynomic");
			break;
		case R.id.more:
			more.setSelected(true);
			tabHost.setCurrentTabByTag("more");
			break;
		}
	}

	void initButtons() {
		home.setSelected(false);
		radar.setSelected(false);
		concertraition.setSelected(false);
		dynomic.setSelected(false);
		more.setSelected(false);
		
	}

}
