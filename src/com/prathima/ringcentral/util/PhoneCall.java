package com.prathima.ringcentral.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.prathima.ringcentral.controller.RingStatusController;

/*
 *  This class has methods to check status of the phone call and to get duration of last call made
 */

public class PhoneCall {
	
	public static RingStatusObject checkStatus(RingStatusObject ringstatusd) throws Exception{
		
		if(ringstatusd != null){
			
			try{
				
				TrustCertsUtil.disableSslVerification();
				String  accessToken= Token.getInstance().getAccess_token();
				URL ringOutURL = new URL(ringstatusd.getUri());
				HttpURLConnection conn = (HttpURLConnection) ringOutURL.openConnection();
				String bearerAuth = "bearer   " + accessToken;
				//System.out.println(bearerAuth );
				conn.setRequestProperty ("Authorization", bearerAuth);
				conn.setRequestMethod("GET");
				conn.setDoOutput(true);			
											
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				StringBuilder sb= new StringBuilder();
				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
					sb.append(output);
					
				}
				
	            RingStatusObject ringstatus = new Gson().fromJson(sb.toString(), RingStatusObject.class);
	            
				/*System.out.println(ringstatus.getId());
				System.out.println(ringstatus.getUri());
				System.out.println(ringstatus.getStatus().get("callStatus"));
				System.out.println(ringstatus.getStatus().get("callerStatus"));
				System.out.println(ringstatus.getStatus().get("calleeStatus"));	*/	
				
				return ringstatus;
				
				
			}
			catch(Exception e){
				throw e;
			}
			
			
			
		}
			
		return null;

}
	
	public static Double getDuration() throws Exception{
		
		try{
			
			TrustCertsUtil.disableSslVerification();
			String  accessToken= Token.getInstance().getAccess_token();
			URL ringOutURL = new URL(RingCentralConstants.CALL_LOG_URL);
			HttpURLConnection conn = (HttpURLConnection) ringOutURL.openConnection();
			String bearerAuth = "bearer   " + accessToken;
			//System.out.println(bearerAuth );
			conn.setRequestProperty ("Authorization", bearerAuth);
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);			
										
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			StringBuilder sb= new StringBuilder();
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				sb.append(output);
				
			}
			
           CallLogObject callLogStatus = new Gson().fromJson(sb.toString(), CallLogObject.class);
            
			List<Map> callLogList = (List<Map>) callLogStatus.getRecords();
			LinkedTreeMap<Object,Object> a = (LinkedTreeMap<Object,Object>) callLogList.get(0);
			return ((Double) a.get("duration"));
			
			/*System.out.println(ringstatus.getUri());
			System.out.println(ringstatus.getStatus().get("callStatus"));
			System.out.println(ringstatus.getStatus().get("callerStatus"));
			System.out.println(ringstatus.getStatus().get("calleeStatus"));	*/	
			
			//return callLogStatus;
			
			
		}
		catch(Exception e){
			throw e;
		}
		
	}
}