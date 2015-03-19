package com.prathima.ringcentral.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;

/*  This Singleton class generates access token to be used with calling RESTFUL API's.
 * When access_token expires, new access_token is generated using the refresh token obtained along with access token.
 * When Refresh Token also expires, new access_token is generated using grant_type password data.
 * It uses java Timer for scheduling a thread to set the expiry of the access token and Refresh token.
 * 
 */
public class Token
{

    private static String access_token = null;
    private static String refresh_token = null;
    private volatile static Token INSTANCE = null;
    private static boolean isAccessExpired = true;
    private static boolean isRefreshExpired = true;
    private static int accessExpTime = 0;
    private static int refreshExpTime = 0;    
    private Timer refreshTimer = null; 
    private Timer accessTimer = null;

	private Token() {
    	
    }
    
	public static int getAccessExpTime() {
		return accessExpTime;
	}

	public static void setAccessExpTime(int accessExpTime) {
		Token.accessExpTime = accessExpTime;
	}

	public static int getRefreshExpTime() {
		return refreshExpTime;
	}

	public static void setRefreshExpTime(int refreshExpTime) {
		Token.refreshExpTime = refreshExpTime;
	}
	
    public static Token getInstance ()
    {
    	
    	if(INSTANCE == null)
    	{
    	  	  synchronized(Token.class)
    	  	  {
    	  		  if(INSTANCE == null){
    	  			INSTANCE = new Token();
    	  		  }
    	  	  }
    	    } 
    	return INSTANCE;
    }
    
      
    
/*
 * 
 */
	public synchronized String getAccess_token() throws Exception
	{
		if(access_token == null || isAccessExpired || isRefreshExpired )
		{
			access_token = generate();
			isAccessExpired = false ;
			isRefreshExpired = false ;
			scheduleAccessTimer();
			scheduleRefreshTimer();
										
		}		
		
		return access_token;
	}
	
	private static void setAccessExpired(boolean isAccessExpired) {
		Token.isAccessExpired = isAccessExpired;
	}

	private static void setRefreshExpired(boolean isRefreshExpired) {
		Token.isRefreshExpired = isRefreshExpired;
	}
	
	private static String getRefresh_token() {
		return refresh_token;
	}

	private static void setRefresh_token(String refresh_token) {
		Token.refresh_token = refresh_token;
	}
	
   private  String generate() throws Exception{
		
	
	
	if(isRefreshExpired && isAccessExpired) {
		try{
			
			System.out.println("Generating Token using grant_type password");
			TrustCertsUtil.disableSslVerification();
			byte[] postData       = RingCentralConstants.GET_TOKEN_PAYLOAD.getBytes( Charset.forName( "UTF-8" ));
			URL tokenURL = new URL(RingCentralConstants.GET_TOKEN_URL);
			HttpURLConnection conn = (HttpURLConnection) tokenURL.openConnection();
			String appCredentials = RingCentralConstants.APP_KEY + ":" + RingCentralConstants.APP_SECRET;
			String basicAuth = "Basic " + Base64Encode.encode(appCredentials);
			// System.out.println(basicAuth );
			conn.setRequestProperty ("Authorization", basicAuth);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			conn.setDoOutput(true);
			conn.getOutputStream().write(postData);
			conn.getOutputStream().flush();		
			
	 
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			StringBuilder sb= new StringBuilder();
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				sb.append(output);
				
			}
			
			JsonObject jobj = new Gson().fromJson(sb.toString(), JsonObject.class);
			
			String accessToken = jobj.get("access_token").toString();
			String refreshToken = jobj.get("refresh_token").toString();
			int refreshExpTime = jobj.get("refresh_token_expires_in").getAsInt();
			int accessExpTime = jobj.get("expires_in").getAsInt();
			
			Token.setRefresh_token(refreshToken.substring(1, refreshToken.length()-1));
			Token.setAccessExpTime(accessExpTime);
			Token.setRefreshExpTime(refreshExpTime);
			
			//System.out.println("accessToken "+accessToken);
			
			//System.out.println("accessToken "+accessToken.substring(1, accessToken.length()-1));
			
			
			return (accessToken.substring(1, accessToken.length()-1));
			
		}
		catch(Exception e){
			e.printStackTrace();	
			throw e ;
		}
	}
	else
	{

		try{
			System.out.println("Generating Token using RefreshToken");
			TrustCertsUtil.disableSslVerification();
			String datatoPost = "grant_type=refresh_token&refresh_token="+Token.getRefresh_token();
			byte[] postData       = datatoPost.getBytes( Charset.forName( "UTF-8" ));
			URL tokenURL = new URL(RingCentralConstants.GET_TOKEN_URL);
			HttpURLConnection conn = (HttpURLConnection) tokenURL.openConnection();
			String appCredentials = RingCentralConstants.APP_KEY + ":" + RingCentralConstants.APP_SECRET;
			String basicAuth = "Basic " + Base64Encode.encode(appCredentials);
			// System.out.println(basicAuth );
			conn.setRequestProperty ("Authorization", basicAuth);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
			conn.setDoOutput(true);
			conn.getOutputStream().write(postData);
			conn.getOutputStream().flush();		
			
	 
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			StringBuilder sb= new StringBuilder();
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				sb.append(output);
				
			}
			
			JsonObject jobj = new Gson().fromJson(sb.toString(), JsonObject.class);
			
			String accessToken = jobj.get("access_token").toString();
			String refreshToken = jobj.get("refresh_token").toString();
			int refreshExpTime = jobj.get("refresh_token_expires_in").getAsInt();
			int accessExpTime = jobj.get("expires_in").getAsInt();
			
			Token.setRefresh_token(refreshToken.substring(1, refreshToken.length()-1));
			Token.setAccessExpTime(accessExpTime);
			Token.setRefreshExpTime(refreshExpTime);
			
			
			return (accessToken.substring(1, accessToken.length()-1));
			
		}
		catch(Exception e){
			e.printStackTrace();	
			throw e ;
		}
		
	}
	}
	
   public static void main(String[] args) throws Exception {
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   	Thread.sleep(3*60*1000);
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   	Thread.sleep(3*60*1000);
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   	Thread.sleep(1*60*1000);
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   	System.out.println("Token" + Token.getInstance().getAccess_token() + " isAccessExpired: " + isAccessExpired + " isRefreshExpired: " + isRefreshExpired);
   }
   
   private void scheduleAccessTimer()
   {
	   System.out.println("Token.getAccessExpTime" + Token.getAccessExpTime());
	   if(accessTimer == null){
		   accessTimer  = new Timer(true);
	   }
	   accessTimer.schedule(new NewAccessToken(), Token.getAccessExpTime()*1000);
	   
   }
   
   private void scheduleRefreshTimer()
   {
	   System.out.println("Token.getRefreshExpTime" + Token.getRefreshExpTime());
	   if(refreshTimer == null)
	   {
		   refreshTimer = new Timer(true);
		   refreshTimer.schedule(new NewRefreshToken(),Token.getRefreshExpTime()*1000 );
	   }
	   else
	   {
		   refreshTimer.cancel();
		   refreshTimer = new Timer(true);
		   refreshTimer.schedule(new NewRefreshToken(),Token.getRefreshExpTime()*1000 );
	   }
	   
   }
   
   private class NewAccessToken extends TimerTask{

	@Override
	public void run() {
		System.out.println("Access Token Expired");
		Token.setAccessExpired(true);		
	}
	   
   }
   
   private class NewRefreshToken extends TimerTask{

		@Override
		public void run() {
			System.out.println("Refresh Token Expired");
			Token.setRefreshExpired(true);		
		}
		   
	   }
       
}


