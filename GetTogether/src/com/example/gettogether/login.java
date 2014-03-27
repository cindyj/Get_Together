package com.example.gettogether;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity implements OnClickListener {
	EditText etUsername;
	EditText etPassword;
	Button btLogin;
	Button btRegisterFromLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		etUsername = (EditText) findViewById(R.id.etLoginUsername);
		etPassword = (EditText) findViewById(R.id.etLoginPassword);
		btLogin = (Button) findViewById(R.id.btLogin);
		btRegisterFromLogin = (Button) findViewById(R.id.btRegisterFromLogin);
		btLogin.setOnClickListener(this);
		btRegisterFromLogin.setOnClickListener(this);

		
		//initialize Parse
		Parse.initialize(this, "U9l8917jhMBXeYAZWWdNYm4DpYtidJK3BgRJRV7v", "VL0S7azV8mc3dyLw6gwp5EDrAmMfg9er9kK9GcfF"); 
		ParseAnalytics.trackAppOpened(getIntent());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() == R.id.btLogin){
			String username = etUsername.getText().toString();
			String password = etPassword.getText().toString();


			//login the user
			ParseUser.logInInBackground(username, password, new LogInCallback() {
				@Override
				public void done(ParseUser user, ParseException e) {
					// TODO Auto-generated method stub
					if (user != null) {
						// Hooray! The user is logged in.
						//start other activity
						try{
							Class ourClass = Class.forName("com.example.gettogether.MainActivity");
							Intent ourIntent = new Intent(login.this, ourClass);
							Log.d("Logging in the User", "I'm sending them to the main screen");
							startActivity(ourIntent);
						}catch(ClassNotFoundException c){
							c.printStackTrace();
						}
					} else {
						// S ignup failed. Look at the ParseException to see what happened.
						Toast.makeText(getApplicationContext(), "Incorrect Login Information", Toast.LENGTH_SHORT).show();
					}
				}
			});
		}

		else if(v.getId() == R.id.btRegisterFromLogin){
			try {
				Class ourClass = Class.forName("com.example.gettogether.register");
				Intent ourIntent = new Intent(login.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
