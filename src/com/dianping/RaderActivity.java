package com.dianping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.dianping.net.HttpInterface;
import com.dianping.net.HttpUtils;

public class RaderActivity extends Activity implements OnClickListener {

	private ImageView rader_range;
	private MapView mapview;
	private MapController controler;
	private LocationOverlay overlay;
	private PopupOverlay pop;
	private View anim_view;
	private View map_content;
	boolean isLocationClientStop = false;
	private LocationManager locationManager;
	private LocationProvider locationProvider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.radar_layout);
		initView();

	}
	
	void initLocation(){
		locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(criteria.ACCURACY_FINE);
		criteria.setCostAllowed(false);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		String provider = locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(provider, 2000, 10, listener);
		Location location = locationManager.getLastKnownLocation(provider);
		while(location == null)
			location = locationManager.getLastKnownLocation(provider);
		updateWidthNewLocation(location);
		locationManager.removeUpdates(listener);
	}
	
	private LocationListener listener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
		
		@Override
		public void onProviderEnabled(String provider) {
		}
		
		@Override
		public void onProviderDisabled(String provider) {
		}
		
		@Override
		public void onLocationChanged(Location location) {
			updateWidthNewLocation(location);
		}
	};
	
	private void updateWidthNewLocation(Location location){
		if(location != null){
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			Log.e("location", "精度 "+lat +"  \r\n  维度  "+lng);
			controler.animateTo(new GeoPoint((int)(lat*1e6), (int)(lng*1e6)));
		}
	}
	
	void initView(){
		this.findViewById(R.id.comment).setOnClickListener(this);
		View rader_start = this.findViewById(R.id.rader_start);
		rader_start.setOnClickListener(this);
		rader_range = (ImageView) this.findViewById(R.id.radar_range);
		mapview = (MapView) this.findViewById(R.id.mapview);
		anim_view = this.findViewById(R.id.radar_anim);
		map_content = this.findViewById(R.id.radar_content);
		mapview.setBuiltInZoomControls(true);
		mapview.getController().setZoom(14);
		controler = mapview.getController();
		controler.enableClick(true);
	}
	
	public void createPopup(){
		PopupClickListener click = new PopupClickListener() {
			
			@Override
			public void onClickedPopup(int arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		pop = new PopupOverlay(mapview, click);
	}

	@Override
	protected void onDestroy() {
		mapview.destroy();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		isLocationClientStop = true;
		mapview.onPause();
		
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapview.onResume();
		super.onResume();
	}
	
	private void range_anim(ImageView img){
		RotateAnimation anim = new RotateAnimation(0, -360, img.getWidth()/2, img.getHeight()/2);
		anim.setDuration(1000);
		anim.setRepeatCount(2);
		img.startAnimation(anim);
		anim.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
				anim_view.setVisibility(View.GONE);
				map_content.setVisibility(View.VISIBLE);
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.rader_start:
			range_anim(rader_range);
			break;
		case R.id.comment:
			initLocation();
			break;
		}
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mapview.onSaveInstanceState(outState);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	mapview.onRestoreInstanceState(savedInstanceState);
    }
    
    private void readRaderMessage(){
    	List<NameValuePair> param = new ArrayList<NameValuePair>();
    	param.add(new BasicNameValuePair("gps_x", "0"));
    	param.add(new BasicNameValuePair("gps_y", "0"));
    	HttpUtils.HttpGetAsync(param, "leida", new HttpInterface() {
			
			@Override
			public void success(String content) {
				
			}
			
			@Override
			public void failed(String content) {
				
			}
		});
    }
	
	public class LocationOverlay extends MyLocationOverlay{

		public LocationOverlay(MapView arg0) {
			super(arg0);
		}
	}

}
