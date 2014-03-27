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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class allEventsListView extends Activity{
	private ListView listView;
	public ArrayList<String> listItems;
	private ArrayAdapter adapter;

 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_events_list_view);

		listView= (ListView) findViewById(R.id.listView1);

		listItems = new ArrayList<String>();

		adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItems);
		listView.setAdapter(adapter);

	} 

	public void updateListItems(){
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

			for(ParseObject event: ob){
				
			}

			//get first object (most recent event)
			ParseObject mostUpcomingEvent = ob.get(0);

			String url = mostUpcomingEvent.getString("url");

			Bitmap x;

		}catch (com.parse.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


