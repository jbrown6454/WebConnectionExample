package com.example.webconnectionexample;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class WebAPITask extends AsyncTask<String, Integer,String>{
	
	private ProgressDialog proglog;
	private Activity activity;
	private Context context;
	public final static String debugTag="WebAPITask";
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		proglog=ProgressDialog.show(this.activity,"Search", "Retrieving Web Data",true,false);
	}
	
	public WebAPITask(Activity activity){
		super();
		this.activity=activity;
		this.context=this.activity.getApplicationContext();
		
	}

	@Override
	protected String doInBackground(String... params) {
		try{
			Log.d(debugTag,"Background:"+Thread.currentThread().getName());
			String result =WebHelper.downloadFromServer(params);
			return result;
		} catch(Exception e){
			return new String();
		}
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		proglog.dismiss();
		if(result.length()==0)
		{
			Toast toast= Toast.makeText(context,"Unable to find data.Please try again later", Toast.LENGTH_LONG);
			toast.show();
			return;
		}
		
		String host="json parser didnt work";
		String jjSON="json object didnt parse";
		try {
			JSONObject json=new JSONObject(result);
			host=json.getString("Host");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		TextView textView=(TextView) activity.findViewById(R.id.textView1);
		textView.setText(host);
		//asign data to variable here
		
	}

	
}
