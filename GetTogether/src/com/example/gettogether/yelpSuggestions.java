package com.example.gettogether;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;

public class yelpSuggestions extends Activity{

	private ListView listView1;
	ArrayList<String> phoneNumbersSelected;
	ArrayList<Suggestion> suggestions;
	SuggestionAdapter adapter;
	HttpClient client;
	String lat = "";
	String longt = "";
	Button btSendMsg;
	CheckBox checkBox;
	ArrayList<String> placesChecked; //keeps track of the places the user checked 
	ArrayList<String> listName;		//keeps track of the 
	ArrayList<String> listContactId;
	ArrayList<String> listMobileNo;
	private static final int REQUEST_CODE = 3;  // The request code for receiving the contact list

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		placesChecked = new ArrayList<String>();
		listName = new ArrayList<String>();
		listContactId = new ArrayList<String>();
		listMobileNo = new ArrayList<String>();
		checkBox = (CheckBox) findViewById(R.id.txtTitle);
		listView1 = (ListView)findViewById(R.id.listView11);
		suggestions = new ArrayList<Suggestion>();
		adapter = new SuggestionAdapter(this, 
				R.layout.listview_item_row, suggestions);

		btSendMsg = (Button) findViewById(R.id.btSendMessage);
		listView1.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		client = new DefaultHttpClient();
		//lv.setAdapter(new CustomYelpAdapter(this, strings, bitmaps));
		//lv.setAdapter(c);
		msgButtonListener();

		new Read().execute("headline", "capsule_review", "url");
	}
	
	public void checkBoxListener(){
		placesChecked.clear();
		for(int i = 0;  i < listView1.getLastVisiblePosition(); i++){
			if(listView1.getChildAt(i)!= null)
			{ 
				if(((CheckBox)listView1.getChildAt(i).findViewById(R.id.txtTitle)).isChecked())
				{  
					//do something
					placesChecked.add(suggestions.get(i).getTitle());
					Log.d("GetTogether", "This is the choice: " + suggestions.get(i).getTitle());
				}
			}
		}
	}

	public void msgButtonListener(){
		btSendMsg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//twilio stuff, open contact screen
				checkBoxListener();
				//new ReadContacts().execute("headline", "capsule_review", "url");
				Class ourClass;
				Intent ourIntent;

				try {
					ourClass = Class.forName("com.example.gettogether.contactList");
					ourIntent = new Intent(yelpSuggestions.this, ourClass);
					startActivityForResult(ourIntent, REQUEST_CODE);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}); 
	} 


	//Retrieves the contacts the user selected from their Contact List and sends SMS message to them
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("GEtTogether", "I'm in onActivity result");
		Log.d("GetTogether", "This is the requestCode: " + requestCode + "and this is the REQUEST_CODE " + REQUEST_CODE);
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		if(requestCode == REQUEST_CODE){
			Log.d("GetTogether", "This is the resultCode " + resultCode + "and this is is the RESULT_OK " + RESULT_OK);
			if(resultCode == RESULT_OK){
				Log.d("GetTogether", "The result is ok"); 
				//get arraylist
				phoneNumbersSelected = data.getStringArrayListExtra("contacts");
				Toast.makeText(this, "this is the number: " + phoneNumbersSelected.get(0), Toast.LENGTH_SHORT).show();
				for(int i = 0; i < phoneNumbersSelected.size(); i++){
					Log.i("GetTogether", "This is the phone number selected: " + phoneNumbersSelected.get(i));
				}
				
				//**************Send SMS message to contacts selected******************
				try {
			         SmsManager smsManager = SmsManager.getDefault(); //initialize SMS manager
			         smsManager.sendTextMessage("9562238617", null, "i just sent u msg", null, null);
			         Toast.makeText(getApplicationContext(), "SMS sent.",
			         Toast.LENGTH_LONG).show();
			      } catch (Exception exc) {
			         Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
			         exc.printStackTrace();
			      }
			}
		}
		
	}
	
	//Retrieves the search query passed in
	public String getSearchQuery(){
		Bundle gotBasket = getIntent().getExtras();
		String s = gotBasket.getString("query"); //get the search query
		s = s.replaceAll("\\s+","+");
		return s;
	}

	//Retrieves the geolocation passed in
	public void getLatLong(){
		Bundle gotBasket = getIntent().getExtras();
		//lat = gotBasket.getString("latitude");
		//longt = gotBasket.getString("longitude");
		lat = "" + 39.9522190;
		longt = "" + -75.1932140;
		Log.d("GetTogether", "This is the latitude retrieved: " + lat);
		Log.d("GEtTogether", "This is the longtitde retrieved: " + longt);
	}

	//Prepares url to search in Yelp API
	public String prepareURL(){
		getLatLong();
		String temp = "http://api.yelp.com/business_review_search?term=yelp&tl_lat=37.9&tl_long=-122.5&br_lat=37.788022&br_long=-122.399797&limit=3&ywsid=OQe8xEi6elyRsyTGaOL-Sw";
		String url = "http://api.yelp.com/business_review_search?term=" + getSearchQuery() + "&lat=" + lat + "&long=" + longt + "&radius=10&limit=5&ywsid=OQe8xEi6elyRsyTGaOL-Sw";
		Log.d("GetTogether", "This is the url: " + url);
		return url;
	}

	//Retrieves data from Yelp API
	public JSONObject retrieveQueryInfo() throws ClientProtocolException, IOException, JSONException{
		StringBuilder url = new StringBuilder(prepareURL());
		HttpGet get = new HttpGet(url.toString()); //GET method
		HttpResponse r = client.execute(get); //RESPONSE
		int status = r.getStatusLine().getStatusCode(); //this will return a number (200 for success)

		if(status == 200){ //call was a success
			HttpEntity e = r.getEntity(); //entity
			String data = EntityUtils.toString(e); //get string data from entity
			JSONObject jsonobject = new JSONObject(data);
			return jsonobject;
		}

		else{
			Log.d("GetTogether", "Call was a fail!");
		}

		return null;
	}

	//Thread to fetch data from Yelp API
	public class Read extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) { //once it finishes, it will call postexecutes
			// TODO Auto-generated method stub
			Log.d("GetTogether", "I'm in doInBackground");
			try {
				JSONObject obj = retrieveQueryInfo();
				JSONArray allArray = obj.getJSONArray("businesses");
				Log.d("GetTogether", "This is the business json array: " + allArray.toString());
				//get firstobject

				for(int i = 0; i < allArray.length(); i++){
					JSONObject firstObj = allArray.getJSONObject(i);
					Log.d("GetTogeter", "This is the first object: " + firstObj.toString());
					String photoUrl = firstObj.getString("photo_url");
					Log.d("GetTogether", "This is the photo url: " + photoUrl);
					String total = "";
					total += firstObj.getString("name");
					total += firstObj.getString("address1");
					total += firstObj.getString("city");
					total += firstObj.getString("state");

					//To fetch images from API
					Bitmap x;
					HttpURLConnection connection;
					try {
						connection = (HttpURLConnection) new URL(photoUrl).openConnection();
						connection.connect();
						InputStream input = connection.getInputStream();
						x = BitmapFactory.decodeStream(input);
						suggestions.add(new Suggestion(x, total));

						Log.d("GetTogether", "This is the size of the array so far: " + suggestions.size());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				//c.notifyDataSetChanged();

			}catch(JSONException e){
				e.printStackTrace();
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			return "";

		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("GetTogether", "I'm in postExecute");
			Log.d("GetTogether", "This is te size of the array so far (post execute): " + suggestions.size());
			adapter.notifyDataSetChanged();
			// TODO Auto-generated method stub
			//SET ALL THE TEXTVIEWS   

		}
	}
	
	//Thread to read contact information from user's phone
	public class ReadContacts extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			Intent intent= new Intent(Intent.ACTION_PICK,  ContactsContract.Contacts.CONTENT_URI);
			Log.d("GetTogether", "I'm about to open the contacts screen");
			startActivityForResult(intent, 1001);
			return null;
		}
		
	}


	
	
}