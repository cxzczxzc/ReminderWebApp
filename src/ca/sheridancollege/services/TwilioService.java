package ca.sheridancollege.services;

import java.net.URI;
import java.net.URISyntaxException;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.ValidationRequest;
import com.twilio.type.PhoneNumber;

import ca.sheridancollege.dao.Dao;

public class TwilioService {
	private Dao dao = new Dao();
    public static final String ACCOUNT_SID = "AC8395cf26838faadd7a421ddf410d600d";
    public static final String AUTH_TOKEN = "b6862ab306ab91988e979ffe8acb0ada";
    public static final String TWILIO_NUMBER = "+14385008288 ";
    
    public String registerNewNumber(String phoneNumber) 
	{
	    	try {
	    	  Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	  	    ValidationRequest validationRequest = ValidationRequest.creator(new PhoneNumber(phoneNumber))
	  	            .create();
	  	      return "You will recieve a call shortly. Here is your validation code: " + validationRequest.getValidationCode().toString();
	  	  }
	    	catch(ApiException e) 
	    	{
	       return e.getMessage();
	    	}
	}
    
    
    public void sendSMS(String phoneNumber, String sms) 
    {
	    	try {
	    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
	
	        Message message = Message.creator(new PhoneNumber(phoneNumber),
	            new PhoneNumber(TWILIO_NUMBER),sms).create();
	
	        System.out.println(message.getSid());
	    	}
	    	catch(Exception e) 
	    	{
	    		System.out.println(e.getMessage());
    	}
    }
    public void call(String phoneNumber, String message, String filename) 
    {
    	Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Call call;
		try {
			call = Call.creator(new PhoneNumber(phoneNumber), new PhoneNumber(TWILIO_NUMBER),
			    new URI("http://ahmasaad.dev.fast.sheridanc.on.ca/javaproject/"+filename)).setTimeout(120).create();
			   System.out.println(call.getSid());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

     
    }

}
