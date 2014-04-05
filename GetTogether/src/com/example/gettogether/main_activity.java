package com.example.gettogether;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;

public class main_activity extends Activity{

	ListView lv;
	Context context;
	ArrayList<String> strings;
	ArrayList<Bitmap> bitmaps;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_events_list_view);
		strings = new ArrayList<String>();
		bitmaps = new ArrayList<Bitmap>();

		context = this;
		lv = (ListView) findViewById(R.id.listView1);
		lv.setAdapter(new CustomAdapter(this, strings, bitmaps));
		fillListView();
	}

	protected void fillListView(){
		ParseUser user = ParseUser.getCurrentUser();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
		query.whereEqualTo("user", user);

		List<ParseObject> ob;
		try {
			ob = query.find();
			query.orderByDescending("date");

			for(ParseObject event: ob){
				String tvToPass = "";
				tvToPass += event.getString("date") + "\n" + event.getDate("name") + "\n" + event.getString("address");
				strings.add(tvToPass);
				String url = event.getString("url");

				Bitmap x;

				HttpURLConnection connection;
				try {
					connection = (HttpURLConnection) new URL(url).openConnection();
					connection.connect();
					InputStream input = connection.getInputStream();
					x = BitmapFactory.decodeStream(input);
					bitmaps.add(x);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


				lv.setAdapter(new CustomAdapter(this, strings, bitmaps));
			}

		}catch (com.parse.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}

