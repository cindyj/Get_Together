package com.example.gettogether;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	Button btUpcomingEvent;
	Button btNewEvent;
	Button btSeeMoreEvents;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//initialize buttons
		btUpcomingEvent = (Button) findViewById(R.id.btUpcomingEvent);
		btNewEvent = (Button) findViewById(R.id.btNewEvent);
		btSeeMoreEvents = (Button) findViewById(R.id.btSeeMoreEvents);
		btUpcomingEvent.setOnClickListener(this);
		btNewEvent.setOnClickListener(this);
		btSeeMoreEvents.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Class ourClass;
		Intent ourIntent;
		
		switch(v.getId()){
		//displays most recent event
		case R.id.btUpcomingEvent:
			try {
				ourClass = Class.forName("com.example.gettogether.upcomingEvent");
				ourIntent = new Intent(MainActivity.this, ourClass);
				Log.d("GetTogether", "I'm about to show the one upcoming event");
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			
		//create new event; directed to Yelp suggestions
		case R.id.btNewEvent:
			try {
				ourClass = Class.forName("com.example.gettogether.createNewEvent");
				ourIntent = new Intent(MainActivity.this, ourClass);
				Log.d("GetTogether", "I'm about to start the create new event screen");
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			
		//listview of upcoming events	
		case R.id.btSeeMoreEvents:
			try {
				ourClass = Class.forName("com.example.gettogether.main_activity");
				ourIntent = new Intent(MainActivity.this, ourClass);
				Log.d("GetTogether", "I'm about to start the upcoming events listview");
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		}
			
	}

}
