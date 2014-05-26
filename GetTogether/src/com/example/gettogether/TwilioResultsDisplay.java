//Class will display Twilio's results from SMS messages 

package com.example.gettogether;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TwilioResultsDisplay extends Activity{
	private ListView listview;
	private ArrayList<String> listItems;
	private ArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twilio_results_listview);
		//initialize listview
		listview = (ListView) findViewById(R.id.twilioListView);
		listItems = new ArrayList<String>();
		adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItems);
		listview.setAdapter(adapter);
	}
	
	public void retrieveTwilioResults(){
		
	}

}
