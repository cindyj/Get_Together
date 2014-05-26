package com.example.gettogether;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.*; 
import com.twilio.sdk.resource.factory.*; 
import com.twilio.sdk.resource.instance.*; 
import com.twilio.sdk.resource.list.*; 


public class TextSender {
	private static final String ACCOUNT_SID = "ACa012014aafd702e3571b57317e79a798"; 
	private static final String AUTH_TOKEN = "6f9833544766f90c555178c680b96224";
	//private static ArrayList<String> idList = new ArrayList<String>(); 
	private static TwilioRestClient client; 
	//private static HttpServletRequest req;
	//private static HttpServletResponse res;
	//private static final String url = "http://twilioservlet.herokuapp.com";
	//private static HttpClient servletClient;
	
	void TextSender(){
		client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
		//servletClient = new DefaultHttpClient();
	}
	
	void sendMessage(String number, String messageBody) throws TwilioRestException{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("To", number)); 
		params.add(new BasicNameValuePair("From", "+15127461522")); 
		params.add(new BasicNameValuePair("Body", messageBody));

		MessageFactory messageFactory = client.getAccount().getMessageFactory(); 
		Message message = messageFactory.create(params); 
		//System.out.println(message.getSid());  
	}
	
	
}
