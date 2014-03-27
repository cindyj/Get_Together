package com.example.gettogether;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

public class contactList extends Activity implements OnClickListener{
	private ListView listView;
	ArrayList<ContactObj> contactObjs;
	ContactObjAdapter adapter;
	ArrayList<String> recipientsSelected; //the phone numbers that were selected to send a message to 
	Button bSelectedRecipients;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_main);
		recipientsSelected = new ArrayList<String>();
		bSelectedRecipients = (Button) findViewById(R.id.btSendMessageContactScreen);
		listView = (ListView) findViewById(R.id.listViewContacts);
		contactObjs = new ArrayList<ContactObj>();

		adapter = new ContactObjAdapter(this, R.layout.listview_contact_item, contactObjs);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		Log.d("GetTogeter", "I'm in contactList Class");
		bSelectedRecipients.setOnClickListener(this);
		new readContacts().execute("Hi", "hi", "hi");
	} 

	protected void addContactsToList(){
		Log.d("GetTogether", "I'm in addContactsToList method");
		Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null); 
		Log.d("GetTogether", "I initialized the curser");
		while (cursor.moveToNext()) { 
			//Log.d("GetTogether", "I'm in the while statement");
			String contactId = cursor.getString(cursor.getColumnIndex( 
					ContactsContract.Contacts._ID)); 
			String hasPhoneBool = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)); 
			//Log.d("GetTogether", "This is the hasPhone boolean: " + Boolean.parseBoolean(hasPhoneBool));
			if (hasPhoneBool.equals("1")) { 
				//Log.d("GetTogether", "It has a phone");
				// You know it has a number so now query it like this
				Cursor phones = getContentResolver().query( ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId, null, null); 
				while (phones.moveToNext()) { 
					String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
					String phoneNumber = phones.getString(phones.getColumnIndex( ContactsContract.CommonDataKinds.Phone.NUMBER));
					contactObjs.add(new ContactObj(name, phoneNumber));

				} 
				phones.close(); 
			}
		}

		cursor.close();

	}

	public void checkBoxListener() throws ClassNotFoundException{
		recipientsSelected.clear();
		for(int i = 0;  i < listView.getLastVisiblePosition(); i++){
			if(listView.getChildAt(i)!= null)
			{ 
				if(((CheckBox)listView.getChildAt(i).findViewById(R.id.cBContactName)).isChecked())
				{  
					//do something
					recipientsSelected.add(contactObjs.get(i).getNumber());
					Log.d("GetTogether", "This is the choice: " + contactObjs.get(i).getNumber());
				}
			}
		}
		//pass results back to YelpSuggestions activity
		Intent data = new Intent(contactList.this, Class.forName("com.example.gettogether.yelpSuggestions"));
		//---set the data to pass back---
		data.putStringArrayListExtra("contacts", recipientsSelected);
		setResult(Activity.RESULT_OK, data);
		//---close the activity---
		finish();
		
	}


	public class readContacts extends AsyncTask<String, Integer, String>{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			addContactsToList();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			adapter.notifyDataSetChanged();
			super.onPostExecute(result);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.btSendMessageContactScreen){
			try {
				checkBoxListener();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
