package com.cmozie.classes;

import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.cmozie.fragclasses.Network;
import com.cmozie.fragclasses.WhatToDrink;


public class JSONParser {
	
	 TextView beerName;
	 String beerNam;
	TextView beerDescription;
	 String description;
	
 public static void getApiResults(){
		
		String baseUrl = "http://api.brewerydb.com/v2/beer/random?key=4b77a2665f85f929d4a87d30bbeae67b";
		String queryString;
		try {
			queryString = URLEncoder.encode(baseUrl,"UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("ERROR-URL", "ENCODING ISSUE");
			queryString = "";
		}
		URL finalURL;
		try {
			 finalURL = new URL(baseUrl);
			 AsyncRequest wtdRequest = new AsyncRequest();
			 wtdRequest.execute(finalURL);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("BAD URL", "URL MALFORMED");
		}
		
		
	}
	
	
	
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			try {
				JSONObject json = new JSONObject(result);
				JSONObject data = json.getJSONObject("data");
				
				Log.i("JSON", result);
				
				// TODO Auto-generated method stub
				//Log.i("URL", result);
				beerNam = data.getString("name");
				description = data.getString("description");
				beerDescription.setText(description);
				beerName.setText(beerNam);
				Log.i("beer", beerNam);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
	}


