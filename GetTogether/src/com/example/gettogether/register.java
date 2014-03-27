package com.example.gettogether;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class register extends Activity{
	EditText etUsername;
	EditText etPhoneNumber;
	EditText etPassword;
	Button btRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		
		etUsername = (EditText) findViewById(R.id.etRegisterUsername);
		etPhoneNumber = (EditText) findViewById(R.id.etRegisterPhoneNumber);
		etPassword = (EditText) findViewById(R.id.etRegisterPassword);
		btRegister = (Button) findViewById(R.id.btRegister);
		
		btRegister.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				registerUser();
			}
			
		});
	}

	
	
	
	public void registerUser(){
		String username = etUsername.getText().toString();
		String phoneNumber = etPhoneNumber.getText().toString();
		String password = etPassword.getText().toString();
		
		ParseUser user = new ParseUser();
		user.setUsername(username);
		user.setPassword(password);
		user.put("phone", phoneNumber);


		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					// Hooray! Let them use the app now.
					try{
						Class ourClass = Class.forName("com.example.gettogether.login");
						Intent ourIntent = new Intent(register.this, ourClass);
						Log.d("Get Together", "I'm about to send them to the logging in screen after registering");
						startActivity(ourIntent);
					}catch(ClassNotFoundException f){
						f.printStackTrace();
					}
				} else {
					// Sign up didn't succeed. Look at the ParseException
					// to figure out what went wrong
				}
			}
		});
	}
}
