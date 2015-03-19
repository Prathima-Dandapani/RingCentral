package com.prathima.ringcentral.util;

public class RingCentralConstants {

    public static final String REST_SERVER_URL = "https://platform.devtest.ringcentral.com";
	public static final String GET_TOKEN_URL = REST_SERVER_URL + "/restapi/oauth/token" ;
	public static final String RINGOUT_URL  = REST_SERVER_URL + "/restapi/v1.0/account/~/extension/~/ringout";
	public static final String CALL_LOG_URL  = REST_SERVER_URL + "/restapi/v1.0/account/~/extension/~/call-log";
	
	public static final String APP_KEY = "cYap9y1TS7mcSJ_5vO9Adg";
	public static final String APP_SECRET = "mqQ3DQSWTN6PLSFQPXKkogUBRcF7KbTReW5M4nNM8ieQ";
	public static final String SANDBOX_LOGIN = "17322764491";
	public static final String SANDBOX_PASSWORD = "w9fFw6wh2Ol4";
	
	public static final String GET_TOKEN_PAYLOAD = "grant_type=password&username="+SANDBOX_LOGIN+"&extension=&password="+SANDBOX_PASSWORD ;
	
}
