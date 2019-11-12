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
    public static final String ACCOUNT_SID = "<TWILIO ACCOUNT SID GOES HERE>";
    public static final String AUTH_TOKEN = "<TWILIO ACCOUNT AUTH TOKEN GOES HERE>";
    public static final String TWILIO_NUMBER = "<TWILIO PHONE NUMBER GOES HERE>";
    /**
     *This method registers a new phone number to send the reminders to by sending a verification code that has to be entered on the web portal
     */
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
    
   /**
    *This method sends a sms to the specified phone number and the contents of the text message
    */
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
    /*
     *This method calls the specified number.
     *The contents of the message that will be played in the call is stored in a TwiML file
     *Twilio Text-to-speech engine reads that file and plays the message
     */
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
