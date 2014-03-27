package com.example.gettogether;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

public class upcomingEvent extends Activity{
	TextView tvUpcomingEventTitle;
	TextView tvUpcomingEventDescription;
	ImageView ivPlaceLogo;
	Button btSendReminderText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upcoming_event);
		tvUpcomingEventTitle = (TextView) findViewById(R.id.tvUpcomingEventTitle);
		tvUpcomingEventDescription = (TextView) findViewById(R.id.tvUpcomingEventDescription);
		ivPlaceLogo = (ImageView) findViewById(R.id.iVPlaceLogo);
		btSendReminderText = (Button) findViewById(R.id.btsendReminderText);

		setupReminderText();
		try {
			updateEventInformation();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setupReminderText(){
		btSendReminderText.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//Twilio API here
			}

		});
	}

	public void updateEventInformation() throws MalformedURLException, IOException{
		ParseUser user = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
		query.whereEqualTo("user", user);
		try {
			wait(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<ParseObject> ob;
		try {
			ob = query.find();
			query.orderByDescending("date");

			//get first object (most recent event)
			ParseObject mostUpcomingEvent = ob.get(0);

			String url = mostUpcomingEvent.getString("url");

			Bitmap x;

			HttpURLConnection connection;
			try {
				connection = (HttpURLConnection) new URL(url).openConnection();
				connection.connect();
				InputStream input = connection.getInputStream();
				x = BitmapFactory.decodeStream(input);
				ivPlaceLogo.setImageBitmap(x);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			tvUpcomingEventTitle.setText("Upcoming Event: " + mostUpcomingEvent.getInt("name"));
			String eventInformation = "";
			eventInformation += mostUpcomingEvent.getDate("date");
			eventInformation += mostUpcomingEvent.getString("address");

			tvUpcomingEventDescription.setText(eventInformation);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
