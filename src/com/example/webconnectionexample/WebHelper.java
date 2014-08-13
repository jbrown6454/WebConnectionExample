package com.example.webconnectionexample;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class WebHelper {

	//private static String WebSiteUrl="http://restframework.herokuapp.com/users/?format=api";//input the url here
	private static String WebSiteUrl="http://headers.jsontest.com/";
	private static final int HTTP_STATUS_OK=200;
	private static byte[] buff=new byte[1024];
	private static final String logTag="LastFMHelper";
	
	public static class ApiException extends Exception{
		private static final long serialVersionUID=1L;
		
		public ApiException(String msg)
		{
			super(msg);
		}
		
		public ApiException(String msg, Throwable thr)
		{
			super(msg,thr);
		}
	}
		
		/*
		 * download most popular tracks in given metro.
		 * @param params search strings
		 * @return Array of json strings returned by the API.
		 * @throws ApiException
		 */
		

	protected static synchronized String downloadFromServer(String... params) 
	throws ApiException
	{
		String retval=null;
		String metro=params[0];
		
		String url=WebSiteUrl+metro;
		
		Log.d(logTag,"Fetching "+url);
		
		HttpClient client=new DefaultHttpClient();
		HttpGet request=new HttpGet(url);
		
		try{
			//execute request
			HttpResponse response=client.execute(request);
			StatusLine status=response.getStatusLine();
			if(status.getStatusCode()!=HTTP_STATUS_OK){
				throw new ApiException("Invalid response from last.fm"+status.toString());
			}
			
			//process the content
			HttpEntity entity=response.getEntity();
			InputStream ist=entity.getContent();
			ByteArrayOutputStream content= new ByteArrayOutputStream();
			
			int readCount=0;
			while((readCount=ist.read(buff))!=-1){
				content.write(buff,0,readCount);
			}
			retval= new String(content.toByteArray());
			
		} catch(Exception e){
			throw new ApiException("Problem connecting to the server "+ e.getMessage(),e);
		}
		
		return retval;
	
	}
	

}


