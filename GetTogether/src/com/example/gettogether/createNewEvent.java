/* This class allows the user to enter a search query such as 'bowling' and will send their query and geolocation to the YelpSuggestions class
 * */
package com.example.gettogether;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createNewEvent extends Activity implements LocationListener{
	Button btSearch;
	EditText etSearchQuery;
	String lat;
	String longt;
	LocationManager locationManager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_new_event);
		btSearch = (Button) findViewById(R.id.btSearchYelp);
		etSearchQuery = (EditText) findViewById(R.id.etSearchQuery);
		lat = "";
		longt = "";
		getLatLong();
		btSearch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Class ourClass;
				Intent ourIntent;
				//go onto Yelp Suggestions class and pass user's search query and their geolocation
				try {
					ourClass = Class.forName("com.example.gettogether.yelpSuggestions");
					ourIntent = new Intent(createNewEvent.this, ourClass);
					Log.d("GetTogether", "I'm in the on click method!!!!");
					String bread = etSearchQuery.getText().toString();
					Bundle basket = new Bundle();
					basket.putString("query", bread);
					basket.putString("latitude", lat);
					basket.putString("longitude", longt);
					ourIntent.putExtras(basket);
					startActivity(ourIntent);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void getLatLong(){
		Log.d("GetTogether", "******I'm in getLatLong");
		Log.d("GetTogether", "******I'm in getLatLong");
		Log.d("GetTogether", "******I'm in getLatLong");

		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean enabled = service
		  .isProviderEnabled(LocationManager.GPS_PROVIDER);

		// check if enabled and if not send user to the GSP settings
		// Better solution would be to display a dialog and suggesting to 
		// go to the settings
		if (!enabled) {
		  Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		  startActivity(intent);
		} 
 
		 // Get the location manager
	    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	    // Define the criteria how to select the locatioin provider
	    Criteria criteria = new Criteria();
	    provider = locationManager.getBestProvider(criteria, false);
	    Location location = locationManager.getLastKnownLocation(provider);

	    // Initialize the location fields
	    if (location != null) {
	      System.out.println("Provider " + provider + " has been selected.");
	      onLocationChanged(location);
	    } else {
	      Log.d("GetTogether", "I'm null");
	    }
		 
 
	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	/* Remove the locationlistener updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d("GetTogether", "I'm in the onLocationChanged method");
		// get latitude and longitude from location manager
		//lat = "" + Math.round(location.getLatitude() * 100.0) / 100.0;     ........ will uncomment this
		//longt = "" + Math.round(location.getLongitude() * 100.0) / 100.0;  ........ will uncomment this
		lat = "" + 39.9522190;
		longt = "" + -75.1932140;
		Log.d("GetTogether", "This is the latitide: " + lat + " and this is the longitude: " + longt);

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	} 

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}
}
