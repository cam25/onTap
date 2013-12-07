package com.cmozie.classes;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.cmozie.fragclasses.Network;

public class SearchAsyncTask extends AsyncTask<URL, Void, String>{
	 TextView beerName;
	 String beerNam;

	 String description;
	
	
	@Override
	protected String doInBackground(URL... urls) {
		// TODO Auto-generated method stub
		String reply = "";
		for (URL url : urls) {
		reply = Network.URLStringResponse(url);	
		}
		return reply;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		
		try {
			JSONObject json = new JSONObject(result);
			JSONArray data = json.getJSONArray("data");
			
			List<Map<String,String>> test = new ArrayList<Map<String,String>>();
			
			Log.i("Description", description);
			Log.i("beerName", beerNam);
			// TODO Auto-generated method stub
			Log.i("ASYNC", result);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	
}
